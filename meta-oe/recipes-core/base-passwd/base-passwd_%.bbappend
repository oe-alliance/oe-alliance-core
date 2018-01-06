FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Remove the nobash patch as all distros have bash now (A lot of things depend on it).
# Using /bin/sh linked to /bin/bash is not the way to go, as it breaks things, e.g. subshell support in Midnight Commander
SRC_URI_remove = "file://nobash.patch"
