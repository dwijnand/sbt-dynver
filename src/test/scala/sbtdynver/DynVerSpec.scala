package sbtdynver

import org.scalacheck._, Prop._

import RepoStates._

object VersionSpec extends Properties("VersionSpec") {
  property("not a git repo")                                = notAGitRepo().version()         ?= "HEAD+20160917-0000"
  property("no commits")                                    = noCommits().version()           ?= "HEAD+20160917-0000"
  property("on commit, w/o local changes")                  = onCommit().version()            ?= "1234abcd"
  property("on commit with local changes")                  = onCommitDirty().version()       ?= "1234abcd+20160917-0000"
  property("on tag v1.0.0, w/o local changes")              = onTag().version()               ?= "1.0.0"
  property("on tag v1.0.0 with local changes")              = onTagDirty().version()          ?= "1.0.0+0-1234abcd+20160917-0000"
  property("on tag v1.0.0 and 1 commit, w/o local changes") = onTagAndCommit().version()      ?= "1.0.0+1-1234abcd"
  property("on tag v1.0.0 and 1 commit with local changes") = onTagAndCommitDirty().version() ?= "1.0.0+1-1234abcd+20160917-0000"
}

object IsSnapshotSpec extends Properties("IsSnapshotSpec") {
  property("not a git repo")                                = notAGitRepo().isSnapshot()         ?= true
  property("no commits")                                    = noCommits().isSnapshot()           ?= true
  property("on commit, w/o local changes")                  = onCommit().isSnapshot()            ?= true
  property("on commit with local changes")                  = onCommitDirty().isSnapshot()       ?= true
  property("on tag v1.0.0, w/o local changes")              = onTag().isSnapshot()               ?= false
  property("on tag v1.0.0 with local changes")              = onTagDirty().isSnapshot()          ?= true
  property("on tag v1.0.0 and 1 commit, w/o local changes") = onTagAndCommit().isSnapshot()      ?= true
  property("on tag v1.0.0 and 1 commit with local changes") = onTagAndCommitDirty().isSnapshot() ?= true
}

object SonatypeSnapshotSpec extends Properties("SonatypeSnapshotSpec") {
  property("on tag v1.0.0 with local changes")                = onTagDirty().sonatypeVersion()     ?= "1.0.0+0-1234abcd+20160917-0000-SNAPSHOT"
  property("on tag v1.0.0 and 1 commit, w/o local changes S") = onTagAndCommit().sonatypeVersion() ?= "1.0.0+1-1234abcd-SNAPSHOT"
  property("on tag v1.0.0, w/o local changes")                = onTag().sonatypeVersion()          ?= "1.0.0"
}

object isVersionStableSpec extends Properties("IsVersionStableSpec") {
  property("not a git repo")                                = notAGitRepo().isVersionStable()         ?= false
  property("no commits")                                    = noCommits().isVersionStable()           ?= false
  property("on commit, w/o local changes")                  = onCommit().isVersionStable()            ?= true
  property("on commit with local changes")                  = onCommitDirty().isVersionStable()       ?= false
  property("on tag v1.0.0, w/o local changes")              = onTag().isVersionStable()               ?= true
  property("on tag v1.0.0 with local changes")              = onTagDirty().isVersionStable()          ?= false
  property("on tag v1.0.0 and 1 commit, w/o local changes") = onTagAndCommit().isVersionStable()      ?= true
  property("on tag v1.0.0 and 1 commit with local changes") = onTagAndCommitDirty().isVersionStable() ?= false
}
