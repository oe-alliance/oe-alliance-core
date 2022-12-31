def opendreambox_srcrev(srcrev, d):
    if oe.utils.inherits(d, 'opendreambox-autorev'):
        return '${AUTOREV}'
    return srcrev
