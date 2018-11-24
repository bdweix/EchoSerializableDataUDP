# Echo Serializable Data UDP Refactoring

This repository is an IntelliJ IDEA JAVA project that was refactored from given code in Computer Networks. The underlying utility classes were not refactored, but are in need of it.

## Requirement 1: GitHub

To begin, all of this code is housed in this repository. This is a public repository that will support collaborators, progress tracking, and store files securely. I think you would be hard pressed to find any professional organization, anywhere in the world, using any type of programming in production, that doesn't rely on Git in some capacity.

**All Computer Science courses should mandate Git repositories for every project. Code should never be emailed, submitted through Canvas, or shared otherwise.**

GitHub has a nice classroom system that makes it possible for instructors to run their courses and receive private assignments from students. More information can be found here: https://classroom.github.com/



## Requirement 2: Standarized Formatting

Imagine       being 		assigned 

​		a 			research      paper 	and

​	 the 						Professor 			says 		you're 			allowed    

​		to 



​			use 	whatever spacing 		and indentation 			 system       you 

want.

Pretty annoying right? **Welcome to every Computer Science class I've had**. In fact, it wasn't until I started programming on my own that I even knew there was such a thing as coding conventions. Since that day I've no longer had to waste my time over braces, spacing, text, because that's all a terrible waste of time. Can you imagine if you had to write a book and instead of focusing on the plot an author had to focus on choosing how to space their words and when to use a period? Sounds like a waste of time, and it is. These conventions make it possible to:

1. Focus on how the code works, not how it looks.
2. Share your code with anyone. Trying to code at any company, anywhere in the world? They are going to mandate your code adheres to a professional format and style guide. This reduces bugs and increases readability.
3. The biggest cost of coding is not writing it, it's maintaining code! If everyone adheres to standards, we can decrease wasted time and get along with making new code and learning.

### Java Coding Conventions

Here is a link to the official coding conventions for Java: https://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html. It was last updated in 1999. Not that that should inherently discount it's validity, but I think it's a sign that it's not as active of a community or language as it once was. Google has thankfully stepped in and released their own coding convention ([linked here](https://github.com/google/google-java-format)). They also make it possible to integrate directly with your IDE (next section) and auto format your code on every save and apply custom organization standards. Less time styling code = more time writing good code.

*Side note: there is a fair argument that other languages (Koplin, Swift, etc.) are much more ubqitious in the professional world and students would be better off learning these, while still getting many of the strongly typed/lower level benefits of Java.*

## Requirement #3: A decent IDE - IntelliJ 

Eclipse is a fair solution and a decent out of the box set up. I wanted something a bit more powerful with built in refactoring tools and professionally used. The Intellij IDEA editor is modern and up to date. It's also $150 a year - a great sign that it works, is updated, and has paying users relying on it to code every day. Thankfully, their community version is free and great for students. Once downloaded, I added the Google Java Format plugin and have it auto-refactor code every time I save using Save Actions. I also integrated my GitHub account and have it pushing my progress to this repository.

This takes a suprising amount of time to get set up. Why doesn't the Computer Science department issue a series of videos that walk students through setting this up? We wouldn't waste any class time and it would ensure every single student has the same, modern editor that adheres to coding conventions.



## Refactoring the Code:

Some things I hate:

- Having one long main function
- Any code that has a sublevel to it (loops within loops, try/catch within a try/catch)
- Irrelevant variable names 
- Comments - unless you're doing something weird, you don't need comments. Your code should be inherently named properly and make sense.
- while(true) loops....
- Unreachable code
- Exceptions - if something can throw an exception, it deserves it's own method with a properly caught exception line

### UDPEchoClient (example code)

Let's start at the top. This is code presented to us:

![Original Code](https://s3.us-east-2.amazonaws.com/aryeo-testing/Screen+Shot+2018-11-23+at+11.08.31+PM.png)

**Import Classes (Lines 2-14):**

The majority of these classes aren't used at all. I have no idea why they are included. Intellij auto removes them for you. 

**Number of Functions (Line 18):**

There is only one function that spans about 40 lines of code. We'll break this down into something much more manageable. 

**Exception Handling (Line 18):**

This entire function throws either an IOException or a ClassNotFoundException. There is no try/catch, but even then it wouldn't tell you anything because there are about a dozen things that could go wrong.

**Argument Checking (Line 20-22):**

I prefer to hard code these parameters into the code. I'll set them as global variables as they can easily be accessed by anyone changing the code.

`private static int serverPort = 8009;`
`private static InetAddress serverAddress = null;`

``serverAddress = InetAddress.getByName("127.0.0.1"); // later in the code`

**Variable Naming (Line 23):**

This object is literally called 'obj', telling me nothing about it's true nature. Upon further analysis this code wasn't totally nescesary to the function at all either, so I removed it. This cleans things up a bit.

**Code Structure:**

There is two main parts to this code, some initilization of functions (BufferedReaders and Server Start Up) and then a while(true) loop that receives requests. Let's start with teh first part and break these into their own initilization functions. We will call these:

`private static void initializeKeyboard()`

`private static void initializeNetwork()`

These both clearly represent what is going on to anyone looking at the code for the first time. We can now break out specific exception handling and know exactly what funciton is going working. The initialized network utilities are referenced throughout the remaining code so we'll make them private variables.

**Consistency:**

Variable implementation appears to follow no standard. We'll follow the google standard and initialize these on a one line basis and as global variables where appropiate. This is what it looked like before:

`String clientInput; // from user`
`String name;`
`echoData toSend, toReceive;`

**Comments:**

I'm removing all comments. Better variable names and broken out functions make them unnecessary.

**While(True) Loop:**

Instead of having a while true loop that is broken by a manual break, I'll turn this into a recursive function. This is much cleaner and makes more sense to a user looking at the code for first time.

**Bracketing:**

There is an old convention that allows for one line for-loops/if statements to be represented without brackets. This is a strange convention and seems to break all Java rules. We will be consistent and wrap all for-loops and if statements with brackets.

**Module Functions:**

I tried to break things up as much as possible by function and readability. For example, there is a block of print statements that already are spaced out. This is a perfect use case for a function *printReceivedData* that simplifies the main method and adds readability to the user.

**Other Details:**

There are a handful of other things I changed, but those were some of the main components. You can see the complete code below. I ran out of time, but I would make additional changes to the underlying utility classes that would let me clean everything up. I don't understand Java exception handling enough to implement a clean solution either, nor do I have enough experience to understand best practices around global variables and when to implement them. Much to learn!

##UDPEchoClient (refactored code)

![Part 1:](https://s3.us-east-2.amazonaws.com/aryeo-testing/Screen+Shot+2018-11-23+at+11.08.53+PM.png)

![Part 2:](https://s3.us-east-2.amazonaws.com/aryeo-testing/Screen+Shot+2018-11-23+at+11.09.12+PM.png)

The resulting code is now broken up into 9 new functions. The Syntax highlighting is slightly off at parts due to the screenshots from Atom, not the IntelliJ editor I wrote it in.

 ### UDPEchoServer (original)
 I won't go indepth how I refactored these. Images below and source code in the repository.
 ![Part 1:](https://s3.us-east-2.amazonaws.com/aryeo-testing/Screen+Shot+2018-11-23+at+11.50.25+PM.png)
 
  ### UDPEchoServer (refactored)
 ![Part 1:](https://s3.us-east-2.amazonaws.com/aryeo-testing/Screen+Shot+2018-11-23+at+11.50.42+PM.png)
