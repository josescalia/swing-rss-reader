package org.josescalia.swingrss.util;

import org.apache.log4j.Logger;
import org.josescalia.swingrss.model.RssHeader;
import org.josescalia.swingrss.model.RssHeaderImage;
import org.josescalia.swingrss.model.RssItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Josescalia
 * Date: 11/28/12
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */
/**
 * A Core class to serve as a core layer of the Rss Reader Application
 * */
public class RssReaderCore {
    private Logger logger = Logger.getLogger(RssReaderCore.class.getName());
    private RssHeader rssHeader;
    private File rssFile;
    private String rssUrl;
    NodeList nodeList;
    Node node;
    Element e;

    public RssReaderCore() {
    }

    public RssReaderCore(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    /**
     * Static function to get XML Url Content, and then save it to temporary xml file*/
    private void getUrlContent() {
        URL url;
        try {
            url = new URL(rssUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;

            //save to this filename
            //String fileName = "C:\\"+ sUrl + ".xml";
            String fileName = "rss_temp.xml";
            rssFile = new File(fileName);

            if (!rssFile.exists()) {
                rssFile.createNewFile();
            }

            //use FileWriter to write file
            FileWriter fw = new FileWriter(rssFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            while ((inputLine = br.readLine()) != null) {
                bw.write(inputLine);
            }

            bw.close();
            br.close();

            logger.info("Creating xml cache done!!");

        } catch (MalformedURLException ex) {
            logger.info("MalFormed Url : " + ex);
        } catch (IOException ioe) {
            logger.info("IOException : " + ioe);
        }
    }

    /**
     * Static function to extract nodes on xml content and casting it to RssHeader object and RssItem List
     * @return RssHeader an object of RssHeader
     * */
    public RssHeader getRssItemList() {
        rssHeader = new RssHeader();
        getUrlContent();
        //File fXmlFile = new File("C:\\Users\\Josescalia\\Downloads\\torrent-feed.xml");
        //List<RssItem> rList = new ArrayList<RssItem>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(rssFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root Element : " + doc.getDocumentElement().getNodeName());
            nodeList = doc.getElementsByTagName("channel");
            if (nodeList.getLength() > 0) { //it is header
                node = nodeList.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    e = (Element) node;
                    rssHeader.setHeaderTitle(getTagValue("title", e));
                    rssHeader.setHeaderDesc(getTagValue("description", e));
                    rssHeader.setHeaderLink(getTagValue("link", e));
                    rssHeader.setHeaderLanguage(getTagValue("language", e));
                }
            }
            //setImageRssHeader
            nodeList = doc.getElementsByTagName("image");
            if(nodeList.getLength()>0){
                node = nodeList.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    e = (Element) node;
                    RssHeaderImage image = new RssHeaderImage();
                    image.setImageTitle(getTagValue("title", e));
                    image.setImageLink(getTagValue("link", e));
                    image.setImageUrl(getTagValue("url", e));
                    rssHeader.setRssHeaderImage(image);
                }
            }

            //getRssItems
            nodeList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    e = (Element) node;
                    RssItem rssItem = new RssItem();
                    rssItem.setTitle(getTagValue("title", e));
                    rssItem.setLink(getTagValue("link", e));
                    rssItem.setGuid(getTagValue("guid", e));
                    rssItem.setPubDate(getTagValue("pubDate", e));
                    rssItem.setCategory(getTagValue("category", e));
                    rssItem.setDescription(getTagValue("description", e));
                    rssHeader.addItem(rssItem);
                }
            }

        } catch (Exception ex) {
            logger.info("Exception :" + ex.getMessage());
            //return null;
        }
        return rssHeader;
    }

    /**
     * static function to get value of given xml element as params
     * @param sTag String tag name of nodes.
     * @param eElement Node of xml to get the value
     * @return String value of node
     * */
    private static String getTagValue(String sTag, Element eElement) {
        try {
            NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
            Node nValue = (Node) nlList.item(0);
            if (nValue.getNodeValue() != null || !nValue.getNodeValue().isEmpty()) {
                return nValue.getNodeValue();
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public RssHeader getRssHeader() {
        return rssHeader;
    }

    public void setRssHeader(RssHeader rssHeader) {
        this.rssHeader = rssHeader;
    }

    public File getRssFile() {
        return rssFile;
    }

    public void setRssFile(File rssFile) {
        this.rssFile = rssFile;
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public static void main(String[] args) throws ParseException {
        String sDate = "Tue, 20 Nov 2012 18:01:38 +0000";
        System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z").parse(sDate)));
    }
}
