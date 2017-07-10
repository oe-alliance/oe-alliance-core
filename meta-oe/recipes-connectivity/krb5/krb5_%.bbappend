# Prevent errors like:
#   error: 'maxsize' may be used uninitialized in this function
# which are false positives?
CFLAGS_append += " -Wno-maybe-uninitialized -Wno-uninitialized"
