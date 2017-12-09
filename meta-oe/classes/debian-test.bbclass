#
# debian-test.bbclass
#
DEBIAN_NATIVE_TESTS ?= ""
SRC_URI_DEBIAN_TEST ?= ""

INHERIT_NATIVE = "${@bb.data.inherits_class('native', d)}"
SRC_URI += "${@bb.utils.contains('INHERIT_NATIVE', 'True', '${SRC_URI_DEBIAN_TEST}', '', d)}"

TEST_DIR ?= "${B}/test"

addtask native_test after do_populate_sysroot
do_native_test[dirs] = "${TEST_DIR}"

def define_debian_test_files(d):
    src = d.getVar('SRC_URI_DEBIAN_TEST', True)
    files = src.replace('file://', '')
    return files

DEBIAN_TEST_FILES = "${@define_debian_test_files(d)}"

do_prepare_native_test() {
	[ -d ${TEST_DIR} ] || mkdir -p ${TEST_DIR}

	for file in ${DEBIAN_TEST_FILES}; do
		cp -a ${WORKDIR}/$file ${TEST_DIR}
	done

	for tst in ${DEBIAN_NATIVE_TESTS}; do
		chmod 755 ${TEST_DIR}/$tst
	done
}

do_native_test() {
	for i in ${DEBIAN_NATIVE_TESTS}; do
		[ -x ./$i ] && ./$i
	done
	do_summary_native_test
}

do_summary_native_test() {
	:
}

addtask prepare_native_test after do_populate_sysroot before do_native_test

# For native package only
python () {
    if not bb.data.inherits_class('native', d):
        bb.build.deltask('do_native_test', d)
}
