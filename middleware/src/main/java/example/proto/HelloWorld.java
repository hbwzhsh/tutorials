/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package example.proto;

@SuppressWarnings("all")
/** Protocol Greetings */
@org.apache.avro.specific.AvroGenerated
public interface HelloWorld {
  public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"HelloWorld\",\"namespace\":\"example.proto\",\"doc\":\"Protocol Greetings\",\"types\":[{\"type\":\"record\",\"name\":\"Greeting\",\"fields\":[{\"name\":\"message\",\"type\":\"string\"}]},{\"type\":\"error\",\"name\":\"Curse\",\"fields\":[{\"name\":\"message\",\"type\":\"string\"}]}],\"messages\":{\"hello\":{\"doc\":\"Say hello.\",\"request\":[{\"name\":\"greeting\",\"type\":\"Greeting\"}],\"response\":\"Greeting\",\"errors\":[\"Curse\"]}}}");
  /** Say hello. */
  example.proto.Greeting hello(example.proto.Greeting greeting) throws org.apache.avro.AvroRemoteException, example.proto.Curse;

  @SuppressWarnings("all")
  /** Protocol Greetings */
  public interface Callback extends HelloWorld {
    public static final org.apache.avro.Protocol PROTOCOL = example.proto.HelloWorld.PROTOCOL;
    /** Say hello. */
    void hello(example.proto.Greeting greeting, org.apache.avro.ipc.Callback<example.proto.Greeting> callback) throws java.io.IOException;
  }
}