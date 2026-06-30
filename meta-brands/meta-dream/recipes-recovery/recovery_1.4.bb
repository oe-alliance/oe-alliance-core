require recovery.inc

SRCREV = "81ff8d40bbe21ec4bff1e397e4afdd2f1f3337e3"
SRCREV:dreamone = "1f997820aa3aa6cafa210a9902d439f4ae4a3721"
SRCREV:dreamtwo = "1f997820aa3aa6cafa210a9902d439f4ae4a3721"

inherit opendreambox-git

BRANCH = "master"

COMPATIBLE_MACHINE = "^(dm520|dm820|dm900|dm920|dm7080|dreamone|dreamtwo)$"
