require recovery.inc

SRCREV = "81ff8d40bbe21ec4bff1e397e4afdd2f1f3337e3"
SRCREV:dreamone = "755c6995cdc8da58a5ad2ff72b77b643cb101d76"
SRCREV:dreamtwo = "755c6995cdc8da58a5ad2ff72b77b643cb101d76"

inherit opendreambox-git

BRANCH = "master"

COMPATIBLE_MACHINE = "^(dm520|dm820|dm900|dm920|dm7080|dreamone|dreamtwo)$"
