# LANcie API
This is the repository for the LANcie-API. The LANcie-API handles everything from registering users to buying tickets and reserving a seat. You can use your own front-end to interact with the API, but you can also use the [LANcie frontend](https://github.com/AreaFiftyLAN/lancie-frontend).

## lancie-api
The API is a Spring based application to suit the needs of a LAN-party.

### Tools
-   [PostgresQL](https://www.postgresql.org/)
-   [mailcatcher](https://mailcatcher.me/), mailcatcher creates a mailserver locally on your pc. All mail sent from the API is cought here, you end up with a mailbox with every outgoing mailaddress. If you have an error installing mailcatcher, you probably do not have Ruby installed. You can find Ruby [here](https://www.ruby-lang.org/en/)

### Run
1.  Import the project into IntelliJ IDEA, we really recommend using [IntelliJ IDEA Ultimate Edition](https://www.jetbrains.com/idea/), since it includes all the support for Spring. You could use another IDE, but we do not recommend this
2.  Enable annotation processing, this can be enabled in `Settings > Build, Execution, Deployment > Compiler > Annotation Processors`. Here you have to check the checkmark that says `Enable Annotation Processors`
3.  Copy `resources/config/application.properties.sample` to `config/application.properties`, fill in the blank fields, required fields are `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`. Please also fill in the `a5l.molliekey` and `a5l.googleMapsAPIkey`, ask your nearest responsible developer for this key. You could also change the url parts of the `AREA FIFTYLAN SETTINGS` to `$(local_frontend_serve_port)/$(tail)`, this is probably something like `https://localhost:5100/${tail}`.  The `#` at the start of the `spring.jpa.nibernate.ddl-auto` also has to be removed.
4.  Go to the Run/Debug Configuration window `Run -> Edit Configurations`, and add a new Spring Boot configuration. It needs only two settings to run. The `Main class` should be set to `ch.wisv.areafiftylan.Application` and the `Use classpath of module` should be set to your project module. Finally, enable the dev profile by entering `dev` in the `Active Profiles` box.

### Run from terminal
After this, it is also possible to start the API directly from the terminal, ommitting the IDE. This can be done by running the `./gradlew bootRunDev` command.

## Deploy
If you want to run the API on your server, you probably don't want to run it from the IDE.

### Build
To generate a runnable JAR file, make sure that you have followed all the instructions under the **run** section. When you have done so, run `./gradlew build`. This command will run all tests, and create a runnable JAR file in the `./build` folder. You could also run `Build` from the gradle view in IntelliJ IDEA.

## Contributing
If you want to contribute, awesome! First, pick an issue and self-assign it. Make your changes in a new branch, with the following naming convention:

* Fixing a bug? > "**fix-**description\_of\_bug"
* Implementing a new feature? > "**feature-**description\_of\_feature"

Once you're satisfied with your changes, create a pull request and give it the label "Ready for merge". You can assign someone in specific or wait for someone to pick it up. Make sure to include tests and documentation. If Travis isn't happy, we're not happy.
