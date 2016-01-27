package sample;


public class Validation {
    public boolean argoValid(String fileText){
        MessageBox msg = new MessageBox();
        if(fileText.contains("UML:Class") && fileText.contains("XMI.exporter>ArgoUML") && fileText.contains("")){
            msg.msgBox("The File is Valid.\nYou can generate the code");
            return true;
        }
        else{
            msg.msgBox("The File is Invalid");
            return false;
        }
    }
}
