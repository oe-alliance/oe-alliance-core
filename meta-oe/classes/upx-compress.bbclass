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
		echo "Let's try and compress: ${line}"
		upx ${UPX_ARGS} "${line}" || true
	    fi
	fi
    done
}
addtask upx before do_package_write_ipk after do_package_qa
