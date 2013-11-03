/*
 * Licensed to ElasticSearch and Shay Banon under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. ElasticSearch licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.search.aggregations;

import org.elasticsearch.search.aggregations.context.AggregationContext;

/**
 * Instantiated per named get in the request (every get type has a dedicated aggregator). The aggregator
 * handles the aggregation by providing the appropriate collector (see {@link #collector()}), and when the aggregation finishes, it is also used
 * for generating the result aggregation (see {@link #buildAggregation()}).
 */
public abstract class AbstractAggregator {

    protected final String name;
    protected final Aggregator parent;
    protected final AggregationContext context;
    protected final int depth;

    protected AbstractAggregator(String name, AggregationContext context, Aggregator parent) {
        this.name = name;
        this.parent = parent;
        this.context = context;
        this.depth = parent == null ? 0 : 1 + parent.depth();
    }

    /**
     * @return  The name of the aggregation.
     */
    public String name() {
        return name;
    }

    /** Return the depth of this aggregator in the aggregation tree. */
    public final int depth() {
        return depth;
    }

    /**
     * @return  The parent aggregator of this aggregator. The addAggregation are hierarchical in the sense that some can
     *          be composed out of others (more specifically, bucket addAggregation can define other addAggregation that will
     *          be aggregated per bucket). This method returns the direct parent aggregator that contains this aggregator, or
     *          {@code null} if there is none (meaning, this aggregator is a top level one)
     */
    public AbstractAggregator parent() {
        return parent;
    }

    public AggregationContext context() {
        return context;
    }

}
