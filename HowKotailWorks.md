Kotail is still in its very early stages of development. It barely accomplishes the goals I've set out to do, but, it is functional. You can run kotail by invoking the executable jar:

```
java -jar kotail.jar
```

From there, you can connect kotail to any number of active jvms, local or remote, by going to Menu->Add Instance.

Kotail will examine all of the mbeans for 0 element signature operations, meaning, currently you cant run any operation that requires your user input to retrieve values with. It also wont allow you to run void return type operations.

The tree will create a top level element for each application instance, another level for each mbean with available operations, and then the operations that bean can invoke.

To start graphing an operation, drag the operation into the top tab portion of the right hand side to start a new graph, or into the body of an existing graph to add to the number of operations being graphed at one time.

|**Kotail examining Ochan with 3 seperate mbeans attributes being graphed at once**|
|:---------------------------------------------------------------------------------|
|![http://kotail.googlecode.com/files/2008-10-18-171702_869x579_scrot.png](http://kotail.googlecode.com/files/2008-10-18-171702_869x579_scrot.png)|

|**JConsole showing a graph for just one attribute at a time, notice the problem**|
|:--------------------------------------------------------------------------------|
|![http://kotail.googlecode.com/files/2008-10-18-171757_898x729_scrot.png](http://kotail.googlecode.com/files/2008-10-18-171757_898x729_scrot.png)|
