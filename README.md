# Demo Game

a simple snake game. to run desktop version

```commandline
./gradlew desktop:run
```

you can compile for browser 

```commandline
./gradlew html:superDev
```
then visit http://localhost:8080/index.html and http://127.0.0.1:9876/

to build standalone fat jar
```commandline
./gradlew desktop:dist
```

To make a deliverable suitable for incorporating into website
```commandline
./gradlew html:dist
```

then 
```commandline
cd html/build/dist
python3 -m http.server
```
and visit http://127.0.0.1:8000/
