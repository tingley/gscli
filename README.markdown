gscli: A command-line tool for the GlobalSight TMS
==================================================

This is a work in progress.  I wrote it to simplify certain types of testing and to provide a framework for automating certain other types of tasks.

Basics
------
* Download the latest [release](https://github.com/tingley/gscli/releases).
* To run: run <code>java -jar gscli-1.0-alpha1.jar</code> (or whatever jar is included in the release).

Building the code
-----------------
gscli builds with [maven](http://maven.apache.org/).

Getting Help
------------
To get a list of commands, run <code>gscli</code> with no arguments.

To get (limited) help for a command, run

<pre>
    gscli help [COMMAND]
</pre>

Profiles
--------
gscli can save credentials for one or more GlobalSight instances/accounts.  Each instance/account pair is known as a "profile".  To add a profile, use

<pre>
    gscli add-profile -url=[URL] -username=[USERNAME] -password=[PASSWORD]
</pre>

The <code>[URL]</code> parameter should include the full path to the application, but not the location of the web services endpoint (for example, http://localhost/globalsight).

Profiles are associated with a "profile name", which defaults to the profile's username.  You can override this by specifying <code>-name=[NAME]</code> to <code>add-profile</code>.  The first profile configured will become the default for future invocations.  To use a non-default profile, you can pass the <code>-profile=[PROFILENAME]</code> argument to any command.  To change the default profile, use the <code>set-default-profile</code> command.

Profile information is stored in a file called <code>.globalsight</code> in the user's home directory.  

<b>WARNING</b>: profile credentials are stored in <b>PLAINTEXT</b>.  If this is a problem you can specify <code>-url</code>, <code>-username</code>, and <code>-password</code> explicitly to any command.

Creating a Job
--------------

The <code>create-job</code> command was the original motivation for writing this tool.  <code>create-job</code> will attempt to do as much as possible on its own:

* Job names are based on the first file uploaded
* File profiles are selected automatically when possible based on file extension
* All target locales are selected by default

For example, the following should work (assuming the existence of a valid profile and a configured file profile for .html files in GlobalSight):

<pre>
    gscli create-job test.html
</pre>

However, if you want to get fancy, there are a number of other options:

* <code>-fileprofile</code> or <code>-fileprofileid</code> to specify the file profile to use (by name and ID, respectively)
* <code>-name</code> to manually specify the job name
* <code>-repeat [N]</code> to repeatedly create the job [N] times in quick succession

License
-------

Like GlobalSight itself, all code is released under the Apache 2.0 license.
