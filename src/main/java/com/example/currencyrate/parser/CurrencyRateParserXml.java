package com.example.currencyrate.parser;

import com.example.currencyrate.model.CurrencyRate;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


@Service
@Component
public class CurrencyRateParserXml implements CurrencyRateParser {

    private static final Logger logger = Logger.getLogger(CurrencyRateParserXml.class);

    @Override
    @Cacheable("listRates")
    public List<CurrencyRate> parse(String ratesFromString) {
        List<CurrencyRate> rates = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();

            try (StringReader reader = new StringReader(ratesFromString)) {
                Document doc = db.parse(new InputSource(reader));
                doc.getDocumentElement().normalize();

                NodeList list = doc.getElementsByTagName("currency");

                for (int i = 0; i < list.getLength(); i++) {
                    Node node = list.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String rate = element.getElementsByTagName("rate").item(0).getTextContent();
                        String currencyCode = element.getElementsByTagName("cc").item(0).getTextContent();
                        CurrencyRate currency = new CurrencyRate(currencyCode, Float.parseFloat(rate));
                        rates.add(currency);
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            logger.error(e.getStackTrace());
        }
        return rates;
    }
}

