PR = "r1"

TZ_PACKAGES_remove = "tzdata-posix tzdata-right"

# to save some space - posix and right dirs will not be present in rootfs
# move tzdata-posix and/or tzdata-right to TZ_PACKAGES if there will be need for posix and right
# directories to be present in rootfs in future
PACKAGES_append = " tzdata-posix tzdata-right"
