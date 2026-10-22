require recovery.inc

SRCREV = "81ff8d40bbe21ec4bff1e397e4afdd2f1f3337e3"
SRCREV:dms922x = "6a605edfa30f302fb22bee39e030e06f6a7e4595"

inherit opendreambox-git

BRANCH = "master"

COMPATIBLE_MACHINE = "^(dm520|dm7435|dm7252s|dms922x)$"
