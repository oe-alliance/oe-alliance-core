DEPENDS:append:class-target = " upx-native"

UPX_ARGS ?= "--best"

# Ensure sstate separates packages when upx version differs
do_upx[vardeps] += "PREFERRED_VERSION_upx-native"

do_upx() {
    echo "UPX - Binary compression"
    find "${WORKDIR}/packages-split" -type f -executable | while read line
    do
	if echo "${line}" | grep -q '/\.debug/'
	then
	    echo "Skipping debug binary: ${line}"
	else
	    if `file -b "${line}" | grep -qe '^ELF 32-bit LSB.*executable'`
	    then
		if command -v upx > /dev/null
		then
		    echo "Let's try and compress: ${line}"
		    upx ${UPX_ARGS} "${line}" || true
		else
		    bbwarn "upx not in PATH, leaving ${line} uncompressed"
		fi
	    fi
	fi
    done
}
addtask upx before do_package_write_ipk after do_package_qa do_prepare_recipe_sysroot

# An interrupted upx run leaves its output file next to the binary, where the
# package QA of the following run reports it as host contamination.
do_package_qa[prefuncs] += "upx_clean_leftovers"

upx_clean_leftovers() {
    if [ -d "${WORKDIR}/packages-split" ]; then
	find "${WORKDIR}/packages-split" -name '*.upx' | while read leftover
	do
	    if [ -f "${leftover%.upx}" ]; then
		bbnote "removing ${leftover#${WORKDIR}/packages-split/} from an interrupted run"
		rm -f "${leftover}"
	    fi
	done
    fi
}
