/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package s_kademlia;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class LaunchNode {
  private static final Logger logger = Logger.getLogger(LaunchNode.class.getName());

  private Server server;

  public void start(KademliaNode knode)
      throws NoSuchAlgorithmException, IOException {
    
    int port = knode.getNode().getPort();
    String host = knode.getNode().getName();
    /* The port on which the server should run */
    server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
        .addService(knode)
        .build()
        .start();
    
    logger.info("Server started, listening on " + host + ":" + port);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // Use stderr here since the logger may have been reset by its JVM shutdown
        // hook.
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        try {
          LaunchNode.this.stop();
        } catch (InterruptedException e) {
          e.printStackTrace(System.err);
        }
        System.err.println("*** server shut down");
      }
    });
  }

  public void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon
   * threads.
   */
  public void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  private void setLoggerLevel(Level level) {
    logger.setLevel(level);
  }

  /**
   * Main launches the server from the command line.
   * 
   * @throws NoSuchAlgorithmException
   */
  public static void main(String[] args) throws IOException, InterruptedException, NoSuchAlgorithmException {
    final LaunchNode server = new LaunchNode();
    if (args.length == 2) {
      KademliaNode knode = new KademliaNode(args[0], Integer.parseInt(args[1]));
      server.start(knode);
    } else if (args.length == 4) {
      KademliaNode knode = new KademliaNode(args[0], Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]));
      server.start(knode);
    } else {
      System.out.println("Usage: java -jar <jarname> <name> <port> [<bootstrapName> <bootstrapPort>]");
      System.exit(0);
    }
    server.setLoggerLevel(Level.FINEST);
    // server.blockUntilShutdown();
  }
}