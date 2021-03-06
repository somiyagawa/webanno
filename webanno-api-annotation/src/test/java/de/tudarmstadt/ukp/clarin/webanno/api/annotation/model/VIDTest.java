/*
 * Copyright 2017
 * Ubiquitous Knowledge Processing (UKP) Lab and FG Language Technology
 * Technische Universität Darmstadt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tudarmstadt.ukp.clarin.webanno.api.annotation.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VIDTest
{
    @Test
    public void test()
    {
        assertEquals("10", new VID(10).toString());
        assertEquals("10.1", new VID(10, 1).toString());
        assertEquals("10.1.2", new VID(10, 1, 2).toString());
        assertEquals("10-1.2.3", new VID(10, 1, 2, 3).toString());
        assertEquals("ext:10-1.2.3", new VID("ext", 10, 1, 2, 3).toString());
        assertEquals("ext:1.10-1.2.3", new VID("ext", 1, 10, 1, 2, 3).toString());
        
        assertEquals(VID.NONE_ID.toString(), VID.parse(VID.NONE_ID.toString()).toString());
        assertEquals("10", VID.parse("10").toString());
        assertEquals("10.1", VID.parse("10.1").toString());
        assertEquals("10.1.2", VID.parse("10.1.2").toString());
        assertEquals("10-1.2.3", VID.parse("10-1.2.3").toString());
        assertEquals("ext:10-1.2.3", VID.parse("ext:10-1.2.3").toString());
        assertEquals("ext:1.10-1.2.3", VID.parse("ext:1.10-1.2.3").toString());
    }
}
