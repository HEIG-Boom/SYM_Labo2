package ch.heigvd.sym.labo2;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import ch.heigvd.sym.labo2.comm.Request;
import ch.heigvd.sym.labo2.comm.SymComManager;
import ch.heigvd.sym.labo2.model.Person;

/**
 * Object activity class used to exchange Java Object with the server
 *
 * @author Jael Dubey, Loris Gilliand, Mateo Tutic, Luc Wachter
 * @version 1.0
 * @since 2019-11-08
 */
public class ObjectActivity extends AppCompatActivity {

    private Button btn;

    private TextView tv;

    private EditText firstName;
    private EditText lastName;
    private EditText phone;

    private RadioGroup choiceParsing;
    private RadioButton JSON;
    private RadioButton XML;

    /**
     * Method called on creation of the activity.
     * Serialize an object representing a person and serialize it in JSON or XML,
     * depending on the user choice. Send data to the server and display the response
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);

        btn = findViewById(R.id.sendObject);
        tv = findViewById(R.id.tWResponseObject);

        tv.setMovementMethod(new ScrollingMovementMethod());


        firstName = findViewById(R.id.eTLastName);
        lastName = findViewById(R.id.eTFirstName);
        phone = findViewById(R.id.eTPhone);

        choiceParsing = findViewById(R.id.rgObjectParsing);

        JSON = findViewById(R.id.ex2_rb_choiceJSON);
        XML = findViewById(R.id.ex2_rb_choiceXML);


        btn.setOnClickListener((v) -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            SymComManager mcm = new SymComManager();

            Person person = new Person(lastName.getText().toString(), firstName.getText().toString(), "John", "male", phone.getText().toString());

            Request request = null;

            if (choiceParsing.getCheckedRadioButtonId() == JSON.getId()) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("firstname", person.getFirstname());
                    jsonObject.put("name", person.getName());
                    jsonObject.put("middlename", person.getMiddlename());
                    jsonObject.put("gender", person.getGender());
                    jsonObject.put("phone", person.getPhone());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String data = jsonObject.toString();

                HashMap<String, String> headers = new HashMap();
                headers.put("Content-Type", "application/json");

                request = new Request("http://sym.iict.ch/rest/json", data, headers);
            } else if (choiceParsing.getCheckedRadioButtonId() == XML.getId()) {
                try {
                    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

                    DocumentBuilder documentBuilder = null;

                    documentBuilder = documentFactory.newDocumentBuilder();


                    Document document = documentBuilder.newDocument();

                    // root element
                    Element root = document.createElement("directory");
                    document.appendChild(root);

                    // employee element
                    Element human = document.createElement("person");

                    root.appendChild(human);


                    // name element
                    Element name = document.createElement("name");
                    name.appendChild(document.createTextNode(person.getName()));
                    human.appendChild(name);

                    // firstname element
                    Element firstName = document.createElement("firstname");
                    firstName.appendChild(document.createTextNode(person.getFirstname()));
                    human.appendChild(firstName);

                    // middlename element
                    Element middlename = document.createElement("middlename");
                    middlename.appendChild(document.createTextNode(person.getMiddlename()));
                    human.appendChild(middlename);

                    // gender element
                    Element gender = document.createElement("gender");
                    gender.appendChild(document.createTextNode(person.getGender()));
                    human.appendChild(gender);

                    // phone element
                    Element phone = document.createElement("phone");

                    Attr attr = document.createAttribute("type");
                    attr.setValue("mobile");
                    phone.setAttributeNode(attr);

                    phone.appendChild(document.createTextNode(person.getPhone()));
                    human.appendChild(phone);

                    StringWriter sw = new StringWriter();
                    TransformerFactory tf = TransformerFactory.newInstance();
                    Transformer transformer = tf.newTransformer();
                    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");


                    DOMImplementation domImpl = document.getImplementation();
                    DocumentType doctype = domImpl.createDocumentType("doctype", "", "http://sym.iict.ch/directory.dtd");

                    transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());

                    transformer.transform(new DOMSource(document), new StreamResult(sw));

                    HashMap<String, String> headers = new HashMap();
                    headers.put("Content-Type", "application/xml");

                    request = new Request("http://sym.iict.ch/rest/xml", sw.toString(), headers);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            mcm.setCommunicationEventListener(
                    response -> {
                        if (choiceParsing.getCheckedRadioButtonId() == JSON.getId()) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                String displayResponse = "Name : " + jsonResponse.getString("name")
                                        + "\nFirstname : " + jsonResponse.getString("firstname")
                                        + "\nMiddlename : " + jsonResponse.getString("middlename")
                                        + "\nGender : " + jsonResponse.getString("gender")
                                        + "\nPhone number : " + jsonResponse.getString("phone");

                                tv.setText(displayResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (choiceParsing.getCheckedRadioButtonId() == XML.getId()) {
                            try {
                                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                                DocumentBuilder builder = factory.newDocumentBuilder();
                                InputSource is = new InputSource(new StringReader(response));
                                Document doc =  builder.parse(is);
                                doc.getDocumentElement().normalize();

                                NodeList nList = doc.getElementsByTagName("person");

                                Node nNode = nList.item(0);

                                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement = (Element) nNode;
                                    String displayResponse = "Name : " + eElement.getElementsByTagName("name").item(0).getTextContent()
                                            + "\nFirstname : " + eElement.getElementsByTagName("firstname").item(0).getTextContent()
                                            + "\nMiddlename : " + eElement.getElementsByTagName("middlename").item(0).getTextContent()
                                            + "\nGender : " + eElement.getElementsByTagName("gender").item(0).getTextContent()
                                            + "\nPhone number : " + eElement.getElementsByTagName("phone").item(0).getTextContent();
                                    tv.setText(displayResponse);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        return true;
                    }
            );
            mcm.sendRequest(request);
        });
    }
}
