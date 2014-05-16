LOCAL_SRC ??= ""

def src_rpm_do_base(d,func):
    bb.build.exec_func(func, d)
    src_uri = d.getVar('SRC_URI')
    d.setVar('SRC_URI', '${LOCAL_SRC}')
    bb.build.exec_func(func, d)
    d.setVar('SRC_URI', src_uri)



python do_unpack () {
    src_rpm_do_base(d,'base_do_unpack')
}

python do_patch () {
    src_rpm_do_base(d,'base_do_patch')
}

