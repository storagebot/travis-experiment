/*
 *   Copyright © 2017 Teclib. All rights reserved.
 *
 *   This file is part of flyve-mdm-android
 *
 * flyve-mdm-android is a subproject of Flyve MDM. Flyve MDM is a mobile
 * device management software.
 *
 * Flyve MDM is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * Flyve MDM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @author    https://stackoverflow.com/users/1190934/james-oravec
 * @date      10/6/17
 * @copyright Copyright © 2017 Teclib. All rights reserved.
 * @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 * @link      https://github.com/flyve-mdm/flyve-mdm-android
 * @link      https://flyve-mdm.com
 * ------------------------------------------------------------------------------
 */
package com.flyvemdm.inventory;

import android.content.Context;

import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import mf.javax.xml.transform.Source;
import mf.javax.xml.transform.stream.StreamSource;
import mf.javax.xml.validation.Schema;
import mf.javax.xml.validation.SchemaFactory;
import mf.javax.xml.validation.Validator;
import mf.org.apache.xerces.jaxp.validation.XMLSchemaFactory;

/**
 * A Utility to help with xml communication validation.
 */public class XmlUtils {

    /**
     * Validation method.
     *
     * @param xml The xml file we are trying to validate.
     * @param ctx The context where is running this function
     * @return True if valid, false if not valid or bad parse or exception/error during parse.
     */
    public static boolean validate(String xml, Context ctx) {

        // Try the validation, we assume that if there are any issues with the validation
        // process that the input is invalid.
        try {
            SchemaFactory  factory = new XMLSchemaFactory();
            InputStream inputStream = ctx.getResources().openRawResource(R.raw.specification);
            Source schemaFile = new StreamSource(inputStream);
            Source xmlSource = new StreamSource(  new ByteArrayInputStream(xml.getBytes()) );
            Schema schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlSource);
        } catch (SAXException e) {
            return false;
        } catch (IOException e) {
            return false;
        } catch (Exception e) {
            // Catches everything beyond: SAXException, and IOException.
            e.printStackTrace();
            return false;
        } catch (Error e) {
            // Needed this for debugging when I was having issues with my 1st set of code.
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
