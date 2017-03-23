# e.g.  FILES_COMPRESS = "/path/bin1 /path/bin2 /path/bin3"
# Define what binaries that we find in the package tree will be compressed
# This variable's definition is MANDATORY if this class is used
# NOTE: path is relative to PKGDEST directory
FILES_COMPRESS ?= ""

DEPENDS_append = " upx-native"

UPX ?= "${STAGING_BINDIR_NATIVE}/upx"
UPX_ARGS ?= "--best -q"

find_and_compress() {
    # Sanity check
    if [ -z ${FILES_COMPRESS} ]; then
        bbdebug 1 "Binary compress class imported but FILES_COMPRESS variable was found empty."
    else
        #Compress
        for pkg in ${PACKAGES}; do
            for bin in ${FILES_COMPRESS}; do
                exec=${PKGDEST}/$pkg$bin
                if [ -x $exec ]; then
                   ${UPX} ${UPX_ARGS} "$exec"
                fi
            done
        done
    fi
}

do_package[postfuncs] += "find_and_compress"
