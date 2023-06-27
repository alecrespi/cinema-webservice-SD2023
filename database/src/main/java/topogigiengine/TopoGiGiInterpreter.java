package topogigiengine;


import exceptions.*;

import javax.inject.Singleton;
import java.util.NoSuchElementException;
import java.util.Scanner;

// This is a utility classed that can be used to convert a query in GiGi query language into TopoGiGiDB workflow
@Singleton
public class TopoGiGiInterpreter {
    private final TopoGigiDB gigi;
    private static TopoGiGiInterpreter gigiSQL = null;

    private TopoGiGiInterpreter() {
        this.gigi = TopoGigiDB.getInstance();
    }

    public static TopoGiGiInterpreter getInstance(){
        if(gigiSQL == null)
            gigiSQL = new TopoGiGiInterpreter();
        return gigiSQL;
    }

    // success when returning a string leaded by #
    // failure otherwise (an HTTP status code)
    public String eval(String query){
        String response = "";
        Scanner input = new Scanner(query);

        String command = "";
        try{
            command = input.next();
        }catch(NoSuchElementException e){
            throw new UnprocessableEntityException();
        }

        try {
            switch (command.toUpperCase()) {
                case "SET": {
                    String key = input.next();
                    String value = input.nextLine().trim();
                    if(value.isEmpty())
                        throw new NoSuchElementException();
                    response = gigi.set(key, value)? "OVERWRITTEN": "OK";
                    break;
                }
                case "GET": {
                    String key = input.next();
                    if(input.hasNext()) // check if remains some non-blank sequence
                        throw new InvalidParametersException();
                    response = gigi.get(key);
                    if(response == null)
                        throw new UndefinedKeyException();
                    break;
                }
                case "REMOVE":{
                    String key = input.next();
                    if(input.hasNext()) // check if remains some non-blank sequence
                        throw new InvalidParametersException();
                    response = gigi.remove(key);
                    if(response == null)
                        throw new UndefinedKeyException();
                    break;
                }
                case "BIND":{
                    String[] keys = input.nextLine().trim().split("\s+");
                    if(gigi.bind(keys))
                        response = "OK";
                    else
                        throw new KeyAlreadyBoundException();
                    break;
                }
                case "RELEASE":
                    break;
                default:
                    throw new UnknownCommandException();
            }
        }catch(NoSuchElementException e){
            throw new InvalidParametersException();
        }
        return response;
    }
}
