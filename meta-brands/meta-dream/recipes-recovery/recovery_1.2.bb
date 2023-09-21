require recovery.inc

SRCREV = "81ff8d40bbe21ec4bff1e397e4afdd2f1f3337e3"
SRCREV:dreamone = "9bb6c14bc8592de0e1d9490021d88489f79c0c3a"
SRCREV:dreamtwo = "9bb6c14bc8592de0e1d9490021d88489f79c0c3a"

inherit opendreambox-git

BRANCH = "master"

COMPATIBLE_MACHINE = "^(dm520|dm820|dm900|dm920|dm7080|dreamone|dreamtwo)$"
