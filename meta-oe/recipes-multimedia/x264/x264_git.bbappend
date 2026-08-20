PR = "r1"

# Avoid the ffmpeg build dependency, which upstream enables by default and
# which risks the circular dependency its own recipe warns about.
PACKAGECONFIG = ""

EXTRA_OECONF:append = ' --extra-asflags="-Wa,--noexecstack"'
