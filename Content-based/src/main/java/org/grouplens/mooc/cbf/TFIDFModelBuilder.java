package org.grouplens.mooc.cbf;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import org.grouplens.lenskit.core.Transient;
import org.grouplens.lenskit.data.event.Rating;
import org.grouplens.lenskit.vectors.MutableSparseVector;
import org.grouplens.lenskit.vectors.SparseVector;
import org.grouplens.lenskit.vectors.VectorEntry;
import org.grouplens.mooc.cbf.dao.ItemTagDAO;
import sun.rmi.runtime.Log;

import javax.inject.Inject;
import javax.inject.Provider;
import java.awt.*;
import java.util.Map;
import java.util.Set;
import java.util.List;

/**
 * The model for a TF-IDF recommender.  The model just remembers the normalized tag vector for each
 * item.
 *
 * @see TFIDFModleBuilder
 * @author <a href="http://www.grouplens.org">GroupLens Research</a>
 */

public class TFIDFModelBuilder implements Provider<TFIDFModel> {
    private final ItemTagDAO dao;
    @Inject
    public TFIDFModelBuilder(@Transient ItemTagDAO dao) {
        this.dao = dao;
    }

    @Override
    public TFIDFModel get() {
        Map<String, Long> tagIds = buildTagIdMap();


        // Create a vector to accumulate document frequencies for the IDF computation
        MutableSparseVector docFreq = MutableSparseVector.create(tagIds.values());
        docFreq.fill(0);
        Map<Long,MutableSparseVector> itemVectors = Maps.newHashMap();

        // Create a work vector to accumulate each item's tag vector.
        // This vector will be re-used for each item.
        // the following part is the core of algorithm
        MutableSparseVector work = MutableSparseVector.create(tagIds.values());
        
        LongSet items = dao.getItemIds();

        for (long item: items) {
            // Reset the work vector for this item's tags.
            work.clear();
            List  aListOfTagsBelongToAItem = dao.getItemTags(item);

            for (Object temp:aListOfTagsBelongToAItem){
                try {
                    Object aIdOfString =  tagIds.get(temp);
                    String st = aIdOfString +"";
                    Long aIdOfString_l = Long.parseLong(st);
                    long stl = aIdOfString_l.longValue();
                    double cc = docFreq.get(stl);
                    docFreq.set(stl, cc + 1);
                }catch (Exception e){

                    Object aIdOfString =  tagIds.get(temp);
                    String st = aIdOfString +"";
                    Long aIdOfString_l = Long.parseLong(st);
                    long stl = aIdOfString_l.longValue();
                    docFreq.set(stl, 1);
                }
            }
            for (Object temp:aListOfTagsBelongToAItem){
                try {
                    Object aIdOfString =  tagIds.get(temp);
                    String st = aIdOfString +"";
                    Long aIdOfString_l = Long.parseLong(st);
                    long stl = aIdOfString_l.longValue();
                    double cc = work.get(stl);
                    work.set(stl,cc + 1);

                }catch (Exception e){

                    Object aIdOfString =  tagIds.get(temp);
                    String st = aIdOfString +"";
                    Long aIdOfString_l = Long.parseLong(st);
                    long stl = aIdOfString_l.longValue();
                    work.set(stl,1);
                }
            }
            itemVectors.put(item, work.shrinkDomain());
            // work is ready to be reset and re-used for the next item
        }

        double dc = 0.0;
        for (MutableSparseVector temSpareVector : itemVectors.values()){
            for (double cc : temSpareVector.values()){
                dc += cc;
            }
        }
        for (VectorEntry e: docFreq.fast()) {
            // Updating this document frequency entry to be a log-IDF value
            docFreq.set(e.getKey(),Math.log10(dc/e.getValue()));
        }
 
        Map<Long,SparseVector> modelData = Maps.newHashMap();
        for (Map.Entry<Long,MutableSparseVector> entry: itemVectors.entrySet()) {
            MutableSparseVector tv = entry.getValue();
            // TODO Convert this vector to a TF-IDF vector
               for (long lc : tv.keySet()){
                   tv.set(lc,tv.get(lc) * docFreq.get(lc));
                      tv.multiply(1/tv.norm());
            // Store a frozen (immutable) version of the vector in the model data.
            modelData.put(entry.getKey(), tv.freeze());
        }

        return new TFIDFModel(tagIds, modelData);
    }

    /**
     * Build a mapping of tags to numeric IDs.
     *
     * @return A mapping from tags to IDs.
     */
    private Map<String,Long> buildTagIdMap() {
        // Get the universe of all tags
        Set<String> tags = dao.getTagVocabulary();
        // Allocate our new tag map
        Map<String,Long> tagIds = Maps.newHashMap();

        for (String tag: tags) {
            // Map each tag to a new number.
            tagIds.put(tag, tagIds.size() + 1L);
        }
        return tagIds;
    }
}
