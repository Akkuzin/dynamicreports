/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2018 Ricardo Mariaca
 * http://www.dynamicreports.org
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.dynamicreports.report.defaults;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import net.sf.dynamicreports.report.defaults.xml.XmlDynamicReports;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)
 */
public class Defaults {

	private static final Logger log = LoggerFactory.getLogger(Defaults.class);

	private static Default defaults;

	static {
		defaults = DefaultBinder.bind(load());
	}

	private static XmlDynamicReports load() {
		String resource = "dynamicreports-defaults.xml";
		InputStream is = null;

        log.debug("Loading default dataTypes and fonts from resource: {}", resource);

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			is = classLoader.getResourceAsStream(resource);
            log.debug("InputStream : {} loaded from classLoader: {}", is, classLoader);
        }
		if (is == null) {
			classLoader = Defaults.class.getClassLoader();
			if (classLoader != null) {
				is = classLoader.getResourceAsStream(resource);
                log.debug("InputStream : {} loaded from classLoader: {}", is, classLoader);
			}
			if (is == null) {
				is = Defaults.class.getResourceAsStream("/" + resource);
                log.debug("InputStream : {} loaded from resource: {}", is, resource);
			}
		}
		if (is == null) {
			return null;
		}

		try {
			Unmarshaller unmarshaller = JAXBContext.newInstance(XmlDynamicReports.class).createUnmarshaller();
            log.debug("Unmarshaller : {} resolved from instance of : {}", unmarshaller, XmlDynamicReports.class);
            JAXBElement<XmlDynamicReports> root = unmarshaller.unmarshal(new StreamSource(is), XmlDynamicReports.class);
			return root.getValue();
		} catch (JAXBException e) {
			log.error("Could not load dynamic reports defaults", e);
			return null;
		}
	}

	public static Default getDefaults() {
        log.debug("Returning defaults {} generated from resource: dynamicreports-defaults.xml", defaults);
        return defaults;
	}
}
