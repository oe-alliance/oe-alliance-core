# Custom Build Configuration output for oe-alliance builds.
# Reorders the printed variable list and replaces per-layer summary with
# a superproject submodule listing.

# YOCTO_CODENAME: current codename + nearest yocto-* tag from openembedded-core.
# - Codename read from oe-core's layer.conf directly (not the expanded
#   LAYERSERIES_CORENAMES var, which bitbake sorts alphabetically). oe-core
#   convention is "oldest ... newest", so [-1] is always current — no dict.
# - Tag from `git describe --tags` provides the anchor release + distance.
# Uses ${@...} inline expression so evaluation happens at getVar() time,
# not at recipe parse (anonymous python in a bbclass runs per-recipe, too
# late for ConfigParsed/BuildStarted where BUILDCFG_VARS is printed).
def _oea_yocto_codename(d):
    import os, re, subprocess
    corebase = d.getVar('COREBASE') or ''
    codename = ''
    try:
        with open(os.path.join(corebase, 'meta', 'conf', 'layer.conf')) as f:
            for line in f:
                m = re.match(r'\s*LAYERSERIES_CORENAMES\s*=\s*"([^"]*)"', line)
                if m:
                    parts = m.group(1).split()
                    if parts:
                        codename = parts[-1]
                    break
    except Exception:
        pass
    tag = ''
    try:
        tag = subprocess.check_output(
            ['git', '-C', corebase, 'describe', '--tags', '--always', '--match', 'yocto-*'],
            stderr=subprocess.STDOUT, text=True).strip()
    except Exception:
        pass
    if codename and tag:
        return '%s (%s)' % (codename, tag)
    return codename or tag

YOCTO_CODENAME = "${@_oea_yocto_codename(d)}"

# BRAND_LAYER: layer in BBLAYERS that provides conf/machine/${MACHINE}.conf.
def _oea_brand_layer(d):
    import os
    machine = d.getVar('MACHINE') or ''
    if not machine:
        return ''
    for l in (d.getVar('BBLAYERS') or '').split():
        if os.path.isfile(os.path.join(l, 'conf', 'machine', machine + '.conf')):
            return os.path.basename(l)
    return ''

BRAND_LAYER = "${@_oea_brand_layer(d)}"

BUILDCFG_VARS = "DISTRO DISTRO_TYPE DISTRO_VERSION DISTRO_FEED_URI MACHINE MACHINEBUILD BRAND_LAYER TARGET_ARCH TARGET_SYS TUNE_FEATURES YOCTO_CODENAME BB_VERSION BUILD_SYS NATIVELSBSTRING SDKMACHINE"

BUILDCFG_FUNCS:remove = "get_layers_branch_rev"
BUILDCFG_FUNCS:append = " oea_repositories_info"

def oea_repositories_info(d):
    import os, re, subprocess, configparser
    import bb.process

    try:
        anchor = d.getVar('COREBASE') or d.getVar('TOPDIR')
        try:
            top, _ = bb.process.run(['git', '-C', anchor, 'rev-parse', '--show-superproject-working-tree'])
            top = top.strip()
        except Exception:
            top = ''
        if not top:
            try:
                top, _ = bb.process.run(['git', '-C', anchor, 'rev-parse', '--show-toplevel'])
                top = top.strip()
            except Exception:
                top = ''
        gm_path = os.path.join(top, '.gitmodules') if top else ''
        if not gm_path or not os.path.isfile(gm_path):
            yield 'oea_repositories_info: no superproject/.gitmodules from %s' % anchor
            return

        gm = configparser.ConfigParser()
        gm.read(gm_path)
        subs = []
        for sec in gm.sections():
            m = re.match(r'submodule\s+"(.+)"', sec)
            if not m:
                continue
            name = m.group(1)
            path = gm.get(sec, 'path', fallback=name)
            url = gm.get(sec, 'url', fallback='')
            subs.append((name, path, url))
        if not subs:
            yield 'Submodules: (no entries in %s)' % gm_path
            return

        def _rev(p):
            try:
                out, _ = bb.process.run(['git', '-C', p, 'rev-parse', 'HEAD'])
                return out.strip()
            except Exception:
                return '<unknown>'

        def _branch(p, name):
            try:
                b, _ = bb.process.run(['git', '-C', p, 'symbolic-ref', '--short', 'HEAD'])
                b = b.strip()
                if b:
                    return b
            except Exception:
                pass
            try:
                b, _ = bb.process.run(['git', '-C', p, 'rev-parse',
                                       '--abbrev-ref', 'HEAD@{upstream}'])
                b = b.strip()
                if b.startswith('origin/'):
                    b = b[len('origin/'):]
                if b:
                    return b
            except Exception:
                pass
            try:
                r, _ = bb.process.run(['git', '-C', p, 'for-each-ref',
                                       '--points-at', 'HEAD',
                                       '--format=%(refname:short)',
                                       'refs/heads', 'refs/remotes'])
                refs = [x.strip() for x in r.splitlines() if x.strip()]
                # prefer origin/<x> and strip the origin/ prefix
                for ref in refs:
                    if ref.startswith('origin/'):
                        return ref[len('origin/'):]
                if refs:
                    return refs[0]
            except Exception:
                pass
            try:
                b, _ = bb.process.run(['git', 'config', '-f', gm_path,
                                       'submodule.%s.branch' % name])
                b = b.strip()
                if b:
                    return b
            except Exception:
                pass
            try:
                b, _ = bb.process.run(['git', '-C', p, 'symbolic-ref',
                                       '--short', 'refs/remotes/origin/HEAD'])
                b = b.strip()
                if b.startswith('origin/'):
                    b = b[len('origin/'):]
                if b:
                    return b
            except Exception:
                pass
            return ''

        def _dirty(p):
            env = os.environ.copy()
            env['PSEUDO_UNLOAD'] = '1'
            base = ['git', '-C', p, 'diff', '--quiet', '--no-ext-diff',
                    '--ignore-submodules=all']
            try:
                subprocess.check_output(base, stderr=subprocess.STDOUT, env=env)
                subprocess.check_output(base + ['--cached'],
                                        stderr=subprocess.STDOUT, env=env)
                return ''
            except subprocess.CalledProcessError:
                return ' [dirty]'
            except Exception:
                return ''

        def _org(url):
            u = (url or '').strip().rstrip('/')
            if u.endswith('.git'):
                u = u[:-4]
            m = re.match(r'(?:git@([^:]+):|https?://([^/]+)/|ssh://([^/]+)/)(.+)$', u)
            if not m:
                return ''
            host = m.group(1) or m.group(2) or m.group(3) or ''
            parts = m.group(4).split('/')
            if len(parts) >= 2:
                return parts[-2]
            return host

        def _origin_url(p):
            try:
                u, _ = bb.process.run(['git', '-C', p, 'remote', 'get-url', 'origin'])
                return u.strip()
            except Exception:
                return ''

        def _reponame(url):
            u = (url or '').strip().rstrip('/')
            if u.endswith('.git'):
                u = u[:-4]
            return u.rsplit('/', 1)[-1] if u else os.path.basename(top)

        # Collect superproject + submodules and sort alphabetically.
        # enigma2 is added separately after this loop and thus stays last.
        super_url = _origin_url(top)
        super_name = _reponame(super_url)
        entries = [(super_name, top, super_url)]
        for name, path, url in subs:
            entries.append((name, os.path.join(top, path), url))
        entries.sort(key=lambda e: e[0])

        def _date(p, rev=None):
            try:
                cmd = ['git', '-C', p, 'log', '-1', '--format=%cs']
                if rev:
                    cmd.append(rev)
                out, _ = bb.process.run(cmd)
                return out.strip()
            except Exception:
                return ''

        # Pre-compute per-entry values so we can pad "branch:sha" to the
        # maximum length and get (org) + date aligned across all rows.
        rows = []
        for name, full, url in entries:
            b, r = _branch(full, name), _rev(full)
            bs = ('%s:%s' % (b, r)) if b else r
            rows.append((name, bs, _org(url), _date(full), _dirty(full)))

        # enigma2 (or per-distro fork) tip — read from BitBake's own
        # BB_URI_HEADREVS persistent cache, populated during recipe parse
        # when AUTOREV is resolved. Same value BitBake uses for do_fetch,
        # without extra network calls. Date read from the local mirror
        # clone in ${DL_DIR}/git2/ if present.
        e2_uri = d.getVar('ENIGMA2_URI') or ''
        m = re.match(r'git://([^/;]+)(/[^;]+)', e2_uri)
        if m:
            host, path = m.group(1), m.group(2)
            proto_m = re.search(r'protocol=([^;]+)', e2_uri)
            proto = proto_m.group(1) if proto_m else 'https'
            bm = re.search(r'branch=([^;]+)', e2_uri)
            e2_branch = bm.group(1) if bm else 'master'
            e2_url = '%s://%s%s' % (proto, host, path)
            e2_name = _reponame(e2_url) or 'enigma2'
            try:
                import bb.fetch2
                # slash-collapse to match git.py:_revision_key
                collapsed = re.sub(r'/+', '.', path)
                key = 'git:' + host + collapsed + e2_branch
                sha = bb.fetch2._revisions_cache.get_rev(key)
                if sha:
                    dl = d.getVar('DL_DIR') or ''
                    mirror = os.path.join(dl, 'git2', host + collapsed)
                    e2_date = _date(mirror, sha) if os.path.isdir(mirror) else ''
                    rows.append((e2_name, '%s:%s' % (e2_branch, sha),
                                 _org(e2_url), e2_date, ''))
            except Exception:
                pass

        # Column widths: longest branch:sha gets exactly 2 spaces to (org).
        bs_width = max((len(bs) for _, bs, _, _, _ in rows), default=0)
        org_width = max((len(o) for _, _, o, _, _ in rows), default=0)

        yield ''
        yield 'Repository state:'
        for name, bs, org, date, dirty in rows:
            pad = ' ' * (bs_width - len(bs))
            org_pad = ' ' * (org_width - len(org))
            date_col = ('  %s' % date) if date else ''
            yield '%-20s = "%s"%s  (%s)%s%s%s' % (
                name, bs, pad, org, org_pad, date_col, dirty)
    except Exception as e:
        yield 'oea_repositories_info: %s' % e
