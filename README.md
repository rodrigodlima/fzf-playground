# fzf-playground
A repository to experiment with fzf, a command-line fuzzy finder

## What is fzf?

fzf is a command-line fuzzy finder. What does that mean? fzf is a tool that finds items based on approximate matching.

Instead of requiring you to type the exact name of a file, command, or item, fzf can find results even if you type only parts of the word, letters out of order, or incomplete fragments.

__Simple example:__
If you have a file called “application-settings.json”
and you type only “apsjs” in fzf, it can still find it.

![find example](images/fzf1.jpg)

So "fuzzy" here means similarity-based searching, non-exact matching, and tolerance for typing errors.

## Some examples


### Find in git log
```
$ git log | fzf
```

With this command, you can search git history.

There are several ways to search text in fzf. If you want an exact word match, you need to put a ' (single quote) at the beginning of the word.


![find example](images/demo.gif)


### Find and checkout branch

```$ git branch |fzf | xargs git checkout``` 

### Tips

fzf also has the ability to turn itself into a different process.

```$ fzf --bind 'enter:become(vim {})'```

Compared to the seemingly equivalent command substitution vim "$(fzf)", this approach has several advantages:

* Vim will not open an empty file when you terminate fzf with CTRL-C
* Vim will not open an empty file when you press ENTER on an empty result
* Can handle multiple selections even when they have whitespaces

Example usage without --bind option. As you can see, after closing fzf, the vim command opens a new blank file


![without bind](images/demo2.gif)

Now, let's try using the --bind option

![with bind](images/demo3.gif)

As you can see, after exiting fzf, vim doesn't create a new file, because the bind option from fzf creates a new process itself
