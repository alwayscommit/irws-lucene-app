1. Login onto Azure VM - 20.105.171.105 using the private key shared in the email
2. The project is already cloned at /home/ranglana/assignment2/irws-lucene-app, you can run a 'git pull' in this folder to get the latest code
3. Run "mvn clean package" after fetching the latest code
4. Use the following command after you build the project successfully. We need to allocate more memory for the program.
   java -Xms2048m -Xmx3084m -jar target/irws-lucene-app-0.0.1-SNAPSHOT.jar server.properties
   server.properties contains filepaths to different datasets and the query filepaths
5. The output file will be generated at '/home/ranglana/assignment2' as 'output.txt' 
6. Run trec_eval -- ../trec_eval/trec_eval qrels-assignment2.part1 output.txt../trec_eval/trec_eval qrels-assignment2.part1 output.txt