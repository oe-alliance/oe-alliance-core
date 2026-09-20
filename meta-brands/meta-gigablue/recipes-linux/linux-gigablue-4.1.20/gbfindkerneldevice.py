#!/usr/bin/python

import os

cmdline = open('/proc/cmdline', 'r').read().split()
args = dict(arg.split('=', 1) for arg in cmdline if '=' in arg)

kerneldevice = args.get('kernel')
if not kerneldevice:
    # Layouts without kernel= keep each kernel right before its rootfs.
    rootfsdevice = args['root']
    digits = len(rootfsdevice) - len(rootfsdevice.rstrip('0123456789'))
    kerneldevice = rootfsdevice[:-digits] + str(int(rootfsdevice[-digits:]) - 1)

if os.path.lexists('/dev/kernel'):
    os.remove('/dev/kernel')
os.symlink(kerneldevice, '/dev/kernel')
