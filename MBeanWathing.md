#This page is discussion of the requirements and design for the mbean watching component of the kotail data recording.

## Requirement ##
A single JVM instance connects to multiple MBeanServer instances, locks in on multiple mbean operations that need to be 'simultaneously' invoked and recorded. Sets of mbeans are organized into a single graph, and share a refresh interval.

## Design ##
A graph is a set of mbeans, each graph has a refresh interval. Each graph registers itself into the watch pool.

**Handling storage**
We have a few options here..
  * EJB3 entities
  * berklydb
  * jroundrobin

**Handling refreshes**
  * Quartz job
  * internal threading
  * Spring batch?
  * What is commons pipeline?