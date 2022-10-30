# Contributing to Sonatel APIs Spring Boot Library

Sonatel APIs Spring Boot Library is released under the Apache 2.0 license.
If you would like to contribute something, or simply want to hack on the code this document should help you get started.

## Using GitHub Issues
We use GitHub issues to track bugs and enhancements.
If you are reporting a bug, please help to speed up problem diagnosis by providing as much information as possible.
Ideally, that would include a [complete & minimal sample project](https://stackoverflow.com/help/minimal-reproducible-example) that reproduces the problem.

## Submitting Pull Requests
This project uses [pull requests](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-requests) for the community to suggest changes to the project.
There are a few important things to keep in mind when submitting a pull request:

* Expect feedback and to make changes to your contributions.
* Unless it is a minor change:
    * It is best to discuss pull requests on an issue before doing work
    * We expect the pull request to start with a [draft pull request](https://github.blog/2019-02-14-introducing-draft-pull-requests/).
        * The pull request should be as small as possible and focus on a single unit of change.
          This ensures that we are collaborating together as soon as possible.
        * Generally, this means do not introduce any new interfaces and as few classes as possible.
        * We will discuss with you how to iterate once you have submitted the initial draft pull request.


## Apache License header

Please add the Apache License header to all new classes, for example:

```java
/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
```

## Code Conventions
None of these is essential for a pull request, but they will all help.  They can also be
added after the original pull request but before a merge.

* Make sure all new `.java` files have a Javadoc class comment with at least an `@author` tag identifying you, and preferably at least a paragraph on what the class is for.
* Add the ASF license header comment to all new `.java` files (copy from existing files in the project).
* Add yourself as an `@author` to the `.java` files that you modify substantially (more than cosmetic changes).
* Add some Javadocs.
* A few unit tests would help a lot as well -- someone has to do it.
* If no-one else is using your branch, please rebase it against the current main branch (or other target branch in the project).


## Squash commits

Use git rebase –interactive, git add –patch and other tools to "squash" multiple commits into atomic changes.

## Format commit messages

* Keep the subject line to 50 characters or less if possible.
* Do not end the subject line with a period.
* In the body of the commit message, explain how things worked before this commit, what has changed, and how things work now.
* Include Fixes gh-<issue-number> at the end if this fixes a GitHub issue.
