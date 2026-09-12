# Human-readable information for OpenATV image archives. The existing
# imageversion/imageinformation files and the receiver's flash layout stay intact.
DEPENDS += "zip-native"

OPENATV_IMAGE_INFO_ARCHIVES ?= "${IMAGE_NAME}.zip ${IMAGE_NAME}_*.zip"
OPENATV_IMAGE_INFO_EXCLUDE ?= ""

python openatv_image_info() {
    import glob
    import os
    import subprocess
    from datetime import datetime
    from pathlib import Path
    from zipfile import ZipFile
    from oe.rootfs import image_list_installed_packages

    if d.getVar('DISTRO') != 'openatv':
        return

    # Older machine hooks write directly into DEPLOY_DIR_IMAGE; newer ones
    # can use IMGDEPLOYDIR. Only handle archives belonging to this image.
    archives = set()
    excluded = set(d.getVar('OPENATV_IMAGE_INFO_EXCLUDE').split())
    for directory in (d.getVar('IMGDEPLOYDIR'), d.getVar('DEPLOY_DIR_IMAGE')):
        for pattern in d.getVar('OPENATV_IMAGE_INFO_ARCHIVES').split():
            for name in glob.glob(os.path.join(directory, pattern)):
                path = Path(name)
                if path.name not in excluded and path.is_file() and not path.is_symlink():
                    archives.add(path)
    if not archives:
        return

    packages = image_list_installed_packages(d)

    def version(*names):
        for name in names:
            value = packages.get(name, {}).get('ver')
            if value:
                return value
            # OE renames kernel-base to kernel-<release>, while retaining
            # kernel-base in Provides. Use the installed provider's version.
            for package in packages.values():
                if name in package.get('provs', []) and package.get('ver'):
                    return package['ver']
        return 'Not installed'

    build_date = datetime.strptime(d.getVar('DATE'), '%Y%m%d').strftime('%Y-%m-%d')
    receiver = ' '.join(filter(None, (d.getVar('MACHINE_BRAND'), d.getVar('MACHINE_NAME'))))
    fields = (
        ('Receiver', receiver),
        ('Machine', d.getVar('MACHINEBUILD') or d.getVar('MACHINE')),
        ('Image Version', d.getVar('IMAGE_VERSION')),
        ('Image Type', d.getVar('DISTRO_TYPE')),
        ('Build Date', build_date),
        ('Enigma2', version('enigma2')),
        ('Linux Kernel', version('kernel-base', 'kernel-image', 'kernel')),
        ('Python', version('python3-core', 'python3')),
        ('OE-Alliance', d.getVar('OE_VER')),
    )
    lines = ['openATV Image Information', '========================', '']
    lines.extend('%-15s: %s' % (label, value or 'Not available') for label, value in fields)
    lines.extend([
        '',
        'Support and Documentation',
        '-------------------------',
        '',
        'Support Forum:',
        'https://www.opena.tv',
        '',
        'User Manual:',
        'https://openatv.github.io/enigma2-doku/en/',
        '',
        'Enigma2 Bug Reports:',
        'https://github.com/openatv/enigma2/issues',
        '',
        'Build System Bug Reports:',
        'https://github.com/oe-alliance/oe-alliance-core/issues',
        '',
        'When reporting a problem, include the receiver model, image version,',
        'steps to reproduce the issue and relevant logs.',
        '',
        'Before Installation',
        '-------------------',
        '',
        'Use only images intended for your receiver model.',
        'Back up your settings and important data before flashing.',
        'Follow the installation instructions for your receiver.',
        '',
    ])
    content = '\n'.join(lines).encode('utf-8')
    # Keep this out of IMAGE_ROOTFS: it is documentation for the ZIP only.
    info = Path(d.getVar('WORKDIR')) / 'openatv-image-info' / 'openatv-image-info.txt'
    info.parent.mkdir(parents=True, exist_ok=True)
    info.write_bytes(content)
    info.chmod(0o644)

    for archive in sorted(archives):
        with ZipFile(archive) as image_zip:
            if info.name in image_zip.namelist() and image_zip.read(info.name) == content:
                continue
        # zip updates an existing entry without duplicates and copies all
        # other compressed members unchanged (no rootfs recompression).
        subprocess.check_call(['zip', '-q', '-j', '-X', str(archive), str(info)])
        with ZipFile(archive) as image_zip:
            if image_zip.namelist().count(info.name) != 1 or image_zip.read(info.name) != content:
                bb.fatal('Unable to add OpenATV image information to %s' % archive)
        bb.note('Added %s to %s' % (info.name, archive.name))
}

IMAGE_POSTPROCESS_COMMAND:append = " openatv_image_info;"
do_image_complete[depends] += "zip-native:do_populate_sysroot"
