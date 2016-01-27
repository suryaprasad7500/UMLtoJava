package sample;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeGen {
    public void gencode(String input) throws IOException {
        String parentName = "";
        int index = 0;
        String classes[][] = new String[100][2];
        String allClasses[] = new String[100];
        Boolean subClass = false;
        String childIDString = new String("childString");
        String parentIDString = "";
        int classCounter = 0;
        int startclass, endclassindex;
        Button save = new Button();
        save.setText("Save in a file");
        Stage window = new Stage();
        Scene scene;
        VBox layout = new VBox(25);
        ProgressBar pb = new ProgressBar();
        pb.setMaxWidth(200);
        layout.getChildren().addAll(pb, save);
        layout.setAlignment(Pos.CENTER);
        scene = new Scene(layout, 300, 250);
        scene.getStylesheets().add("sample/main.css");
        //Code Generation
        //All the required regular expressions start here
        Pattern pattern = Pattern.compile("(?ims)<uml:class (.*?)<\\/UML:Class>");
        Pattern namepattern = Pattern.compile("(?i)name = \'[a-z]*\'");
        Pattern singleclass = Pattern.compile("(?s)<UML:Class [a-z|A-Z|.*|[\\s]*|[0-9]|=*|[']*|[-]*|[:]*|>]*");
        Pattern singleid = Pattern.compile("(?s)xmi.id = '[0-9|[-]*|[a-z|A-Z]*|[:]*]*'[^a-z]");
        Pattern singleGeneralid = Pattern.compile("(?s)xmi.idref = '[0-9|[-]*|[a-z|A-Z]*|[:]*]*'[^a-z]");
        Pattern singlevisibility = Pattern.compile("(?s)visibility = '[0-9|[-]*|[a-z|A-Z]*|[:]*]*'[^a-z]");
        Pattern attributepattern = Pattern.compile("(?ims)<uml:attribute .*>.*</uml:attribute>");
        Pattern datatypepattern = Pattern.compile("(?ims)<uml:datatype (.*?)\\/>");
        Pattern generalTagPattern = Pattern.compile("(?ims)<uml:generalization (.*?)\\/UML:Generalization>\n");
        Pattern generalChildPattern = Pattern.compile("(?ims)<uml:generalization.child>(.*?)<\\/uml:generalization.child>");
        Pattern generalParentPattern = Pattern.compile("(?ims)<uml:generalization.parent>(.*?)<\\/uml:generalization.parent>");
        Pattern operationPattern = Pattern.compile("(?ims)<uml:operation .*>.*</uml:operation>");
        Pattern operationSingle = Pattern.compile("(?ims)<uml:operation .*e'>");
        Pattern parameterCheck = Pattern.compile("(?is)<UML:Parameter[a-z|A-Z|.|\\s|=|'|\\-|0-9|:]*/>");
        //All the required regular expressions end here
        Matcher match = pattern.matcher(input);
        ClassEntity classObj = new ClassEntity();
        Attribute attrObj = new Attribute();
        Operation operObj = new Operation();
        while (match.find()){
            classObj.tag = match.group();
            //System.out.println(classObj.tag);
            Matcher singlematch = singleclass.matcher(classObj.tag);
            while(singlematch.find()){
                classCounter++;
                classObj.single = singlematch.group();
                //System.out.println(classObj.single);
                Matcher namematch = namepattern.matcher(classObj.single);
                Matcher idmatch = singleid.matcher(classObj.single);
                Matcher visibilitycheck = singlevisibility.matcher(classObj.single);
                while(namematch.find() && idmatch.find() && visibilitycheck.find()){
                    int start = namematch.start();
                    int end = namematch.end();
                    classObj.name = classObj.single.substring(start + 8, end - 1);
                    allClasses[classCounter] = classObj.name;
                    start = idmatch.start();
                    end = idmatch.end();
                    classObj.id = classObj.single.substring(start + 10, end - 2);
                    Matcher generalfulltag = generalTagPattern.matcher(input);
                        while (generalfulltag.find()) {
                            int generalTagStartIndex = generalfulltag.start();
                            int generalTagEndIndex = generalfulltag.end();
                            String generalTagFull = input.substring(generalTagStartIndex, generalTagEndIndex);
                            //System.out.println(generalTagFull);
                            Matcher childmatch = generalChildPattern.matcher(generalTagFull);
                            Matcher parentmatch = generalParentPattern.matcher(generalTagFull);
                            while (childmatch.find() && parentmatch.find()) {
                                int childTagStart = childmatch.start();
                                int childTagEnd = childmatch.end();
                                String childTagFull = generalTagFull.substring(childTagStart, childTagEnd);
                                Matcher childID = singleGeneralid.matcher(childTagFull);
                                while (childID.find()){
                                    int childIDStart = childID.start();
                                    int childIDEnd = childID.end();
                                    childIDString = childTagFull.substring(childIDStart+13, childIDEnd-2);
                                    //System.out.println("Child ID "+childIDString);
                                    classes[classCounter][0] = childIDString;
                                    //System.out.println(classes[classCounter][0]);
                                }
                                int parentTagStart = parentmatch.start();
                                int parentTagEnd = parentmatch.end();
                                String parentTagFull = generalTagFull.substring(parentTagStart, parentTagEnd);
                                //System.out.println(parentTagFull);
                                Matcher parentID = singleGeneralid.matcher(parentTagFull);
                                while (parentID.find()){
                                    int parentIDStart = parentID.start();
                                    int parentIDEnd = parentID.end();
                                    parentIDString = parentTagFull.substring(parentIDStart+13, parentIDEnd-2);
                                    //System.out.println("Parent ID: "+parentIDString);
                                    classes[classCounter][1] = parentIDString;
                                }
                            }
                        }
                    start = visibilitycheck.start();
                    end = visibilitycheck.end();
                    classObj.visibility = classObj.single.substring(start + 14, end - 2);
                    //System.out.println("This is the test "+classObj.id);
                    for(int i = 1; i <= classCounter; i++){
                        if (classObj.id.equals(classes[i][0])){
                            subClass = true;
                            index = i;
                        }
                        else{
                            subClass = false;
                        }
                    }
                    /*for (int i = 0; i < allClasses.length; i++){
                        System.out.println(allClasses[i]+"\n");
                    }*/
                    if (!subClass){
                        System.out.println(classObj.visibility+" "+"class "+classObj.name+"{\n");
                    }
                    else if (subClass){
                        //System.out.println("Inherited class here");
                        parentName = allClasses[index-1];
                        System.out.println(classObj.visibility+" "+"class "+classObj.name+" extends "+parentName+" {\n");
                    }
                    /*String filename = classObj.name+".java";
                    File output = new File(filename);
                    PrintWriter fileoutput = new PrintWriter(new FileWriter(output, true));
                    String print = "classObj.visibility+\" \"+\"class \"+classObj.name+\"{\n\"";
                    fileoutput.append(print);*/
                    Matcher matchattr = attributepattern.matcher(classObj.tag);
                    while (matchattr.find()){
                        int attrtagstart = matchattr.start();
                        int attrtagend = matchattr.end();
                        attrObj.tag = classObj.tag.substring(attrtagstart, attrtagend);
                        Matcher attrnamematch = namepattern.matcher(attrObj.tag);
                        Matcher attridmatch = singleid.matcher(attrObj.tag);
                        Matcher attrvisibility = singlevisibility.matcher(attrObj.tag);
                        Matcher attrDataType = datatypepattern.matcher(attrObj.tag);
                        while (attrnamematch.find()|attrDataType.find()){
                            int attrnamestart = attrnamematch.start();
                            int attrnameend = attrnamematch.end();
                            attrObj.name = attrObj.tag.substring(attrnamestart+8, attrnameend-2);
                            int dataTypeStart = attrDataType.start();
                            int dataTypeEnd = attrDataType.end();
                            String dataType = attrObj.tag.substring(dataTypeStart+22, dataTypeEnd-3);
                            if(dataType.contains("087C")){
                                attrObj.dataType = "int";
                            }
                            else if(dataType.contains("087E")){
                                attrObj.dataType = "String";
                            }
                            System.out.println("\t"+attrObj.dataType+" "+attrObj.name+";\n");
                            //System.out.println(attrObj.dataType);
                        }
                    }
                    Matcher matchOper = operationPattern.matcher(classObj.tag);
                    while (matchOper.find()){
                        int operTagStart = matchOper.start();
                        int operTagEnd = matchOper.end();
                        operObj.tag = classObj.tag.substring(operTagStart, operTagEnd);
                        Matcher operSingle = operationSingle.matcher(operObj.tag);
                        while(operSingle.find()){
                            int operSingleIndexStart = operSingle.start();
                            int operSingleIndexEnd = operSingle.end();
                            operObj.single = operObj.tag.substring(operSingleIndexStart, operSingleIndexEnd);
                            //System.out.println(operObj.single);
                            Matcher openVisibilityChecker = singlevisibility.matcher(operObj.single);
                            Matcher operNameChecker = namepattern.matcher(operObj.single);
                            Matcher parameterChecker = parameterCheck.matcher(operObj.tag);
                            Matcher paraNameCheck = namepattern.matcher(operObj.tag);
                            //System.out.println(operObj.tag);
                            while(operNameChecker.find() | openVisibilityChecker.find() | parameterChecker.find()){
                                int operNameStart = operNameChecker.start();
                                int operNameEnd = operNameChecker.end();
                                operObj.name = operObj.tag.substring(operNameStart+8, operNameEnd-1);
                                //System.out.println(operName);
                                int operVisibilityStart = openVisibilityChecker.start();
                                int operVisibilityEnd = openVisibilityChecker.end();
                                operObj.visi = operObj.tag.substring(operVisibilityStart+14, operVisibilityEnd-2);
                                //System.out.println(operVisi);
                                if (parameterChecker.find()){
                                    int paraTagStart = paraNameCheck.start();
                                    int paraTagEnd = paraNameCheck.end();
                                    String paraTag = operObj.tag.substring(paraTagStart, paraTagEnd);
                                    System.out.println(paraTag);
                                }
                                System.out.println("\t"+operObj.visi+" "+operObj.name+"(){\n\t}");
                            }
                        }
                    }
                }
               // while ()
                System.out.println("}\n");
            }
        }
        //Show window
        //window.setScene(scene);
        //window.show();
    }
}

class ClassEntity{
    String tag;
    String single;
    String id;
    String name;
    String visibility;

}

class Attribute{
    String tag;
    String id;
    String name;
    String visibility;
    String dataType;
}

class Operation{
    String visi;
    String tag;
    String single;
    String name;
    String paraTag;
}
