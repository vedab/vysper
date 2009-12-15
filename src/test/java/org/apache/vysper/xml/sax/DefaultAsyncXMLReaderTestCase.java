/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.vysper.xml.sax;

import org.apache.mina.common.ByteBuffer;
import org.apache.vysper.charset.CharsetUtil;
import org.apache.vysper.xml.sax.impl.DefaultAsyncXMLReader;
import org.xml.sax.SAXException;


/**
 * @author The Apache MINA Project (dev@mina.apache.org)
 */
public class DefaultAsyncXMLReaderTestCase extends AbstractAsyncXMLReaderTestCase {

	public void testParseAfterFatalError() throws Exception {
		TestHandler handler = new TestHandler();
		AsyncXMLReader reader = new DefaultAsyncXMLReader();
		reader.setContentHandler(handler);
		reader.setErrorHandler(handler);

		// causes a fatal error
		reader.parse(ByteBuffer.wrap("<root></error>".getBytes("UTF-8")), CharsetUtil.UTF8_DECODER);
		
		try {
			// not allowed to parse after an error
			reader.parse(ByteBuffer.wrap("<root>".getBytes("UTF-8")), CharsetUtil.UTF8_DECODER);
			fail("Must throw SAXException");
		} catch(SAXException e) {
			// OK
		}
	}

	
	public void testParseAfterEndDocument() throws Exception {
		TestHandler handler = new TestHandler();
		AsyncXMLReader reader = new DefaultAsyncXMLReader();
		reader.setContentHandler(handler);
		reader.setErrorHandler(handler);

		// causes a fatal error
		reader.parse(ByteBuffer.wrap("<root></root>".getBytes("UTF-8")), CharsetUtil.UTF8_DECODER);
		
		try {
			// not allowed to parse after end of document
			reader.parse(ByteBuffer.wrap("<root>".getBytes("UTF-8")), CharsetUtil.UTF8_DECODER);
			fail("Must throw SAXException");
		} catch(SAXException e) {
			// OK
		}
	}

}