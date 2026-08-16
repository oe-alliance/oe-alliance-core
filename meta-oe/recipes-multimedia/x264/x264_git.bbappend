# Avoid the ffmpeg build dependency, which upstream enables by default and
# which risks the circular dependency its own recipe warns about.
PACKAGECONFIG = ""
