# Build summary printed when a build finishes. Replaces oe-core's
# buildstats-summary, which reports sstate reuse only, and adds build
# duration, average parallelism, disk I/O, ccache result and the tasks
# that took longest. All numbers come from the buildstats data that
# buildstats.bbclass writes anyway.

OEA_BUILDSUMMARY_SLOWEST ?= "10"

def _oea_bs_time(sec):
    sec = int(sec)
    if sec >= 3600:
        return '%dh%dm%ds' % (sec // 3600, (sec % 3600) // 60, sec % 60)
    if sec >= 60:
        return '%dm%ds' % (sec // 60, sec % 60)
    return '%ds' % sec

def _oea_bs_bytes(count):
    count = float(count)
    for unit in ('B', 'KiB', 'MiB', 'GiB'):
        if count < 1024.0 or unit == 'GiB':
            return '%.1f %s' % (count, unit)
        count /= 1024.0

def _oea_bs_ccache(d):
    import os, subprocess
    if 'ccache' not in (d.getVar('INHERIT') or '').split():
        return None
    env = dict(os.environ)
    for v in ('CCACHE_DIR', 'CCACHE_CONFIGPATH', 'CCACHE_MAXSIZE'):
        val = d.getVar(v)
        if val:
            env[v] = val
    try:
        out = subprocess.check_output(['ccache', '--print-stats'], env=env,
                                      stderr=subprocess.DEVNULL, text=True)
    except Exception:
        return None
    st = {}
    for line in out.splitlines():
        parts = line.split()
        if len(parts) == 2:
            try:
                st[parts[0]] = int(parts[1])
            except ValueError:
                pass
    return st

def _oea_bs_dir(d):
    import os
    base = d.getVar('BUILDSTATS_BASE')
    name = d.getVar('BUILDNAME')
    if not base or not name:
        return ''
    return os.path.join(base, name)

def _oea_bs_pressure(bsdir, duration):
    import os
    out = []
    for res in ('cpu', 'io', 'memory'):
        stalled = 0
        try:
            with open(os.path.join(bsdir, 'reduced_proc_pressure', res + '.log')) as f:
                for line in f:
                    fields = line.split()
                    if len(fields) == 4:
                        stalled += int(fields[3])
        except Exception:
            continue
        out.append('%s %.1f%%' % (res, 100.0 * stalled / 1e6 / duration))
    return ', '.join(out)

def _oea_bs_report(bsdir, sstatetasks, slowest_n, cc_start, cc_now, machine, machinebuild):
    import collections, os, time

    now = time.time()
    started = 0.0
    try:
        with open(os.path.join(bsdir, 'build_stats')) as f:
            for line in f:
                if line.startswith('Build Started:'):
                    started = float(line.split(':', 1)[1])
                    break
    except Exception:
        pass

    built = collections.defaultdict(lambda: [set(), set()])
    slowest = []
    cpu = 0.0
    blocks_in = blocks_out = 0
    failed = []

    for pf in sorted(os.listdir(bsdir)):
        taskdir = os.path.join(bsdir, pf)
        if pf == 'reduced_proc_pressure' or not os.path.isdir(taskdir):
            continue
        tasks = os.listdir(taskdir)
        for t in sstatetasks:
            no_sstate, sstate = built[t]
            if t in tasks:
                no_sstate.add(pf)
            elif t + '_setscene' in tasks:
                sstate.add(pf)
        for t in tasks:
            elapsed = 0.0
            try:
                with open(os.path.join(taskdir, t)) as f:
                    for line in f:
                        k, _, v = line.partition(': ')
                        v = v.strip()
                        if k == 'Elapsed time':
                            elapsed = float(v.split()[0])
                        elif k in ('rusage ru_utime', 'rusage ru_stime',
                                   'Child rusage ru_utime', 'Child rusage ru_stime'):
                            cpu += float(v)
                        elif k in ('rusage ru_inblock', 'Child rusage ru_inblock'):
                            blocks_in += int(float(v))
                        elif k in ('rusage ru_oublock', 'Child rusage ru_oublock'):
                            blocks_out += int(float(v))
                        elif k == 'Status' and v == 'FAILED':
                            failed.append((pf, t))
            except Exception:
                continue
            if elapsed:
                slowest.append((elapsed, pf, t))

    rows = []

    if started:
        duration = now - started
        text = _oea_bs_time(duration)
        if cpu and duration > 0:
            text += ', %.1f cores busy on average' % (cpu / duration)
        rows.append(('Duration', text))
        pressure = _oea_bs_pressure(bsdir, duration) if duration > 0 else ''
        if pressure:
            rows.append(('Stalled on', pressure))

    if blocks_in or blocks_out:
        rows.append(('Disk I/O', '%s read, %s written' % (
            _oea_bs_bytes(blocks_in * 512), _oea_bs_bytes(blocks_out * 512))))

    if cc_now:
        def hits(st):
            return st.get('direct_cache_hit', 0) + st.get('preprocessed_cache_hit', 0)
        h, m = hits(cc_now), cc_now.get('cache_miss', 0)
        scope = 'lifetime'
        if cc_start:
            h -= hits(cc_start)
            m -= cc_start.get('cache_miss', 0)
            scope = 'this build'
        if h + m > 0:
            rows.append(('ccache', '%.0f%% hits %s (%d of %d), %s stored' % (
                100.0 * h / (h + m), scope, h, h + m,
                _oea_bs_bytes(cc_now.get('cache_size_kibibyte', 0) * 1024))))

    active = [(t, built[t]) for t in sstatetasks if built[t][0] | built[t][1]]
    if active:
        reused = sum(len(s) for _, (_, s) in active)
        scratch = sum(len(n) for _, (n, _) in active)
        rows.append(('sstate reuse', '%.1f%% of %d tasks run (%d setscene, %d scratch)' % (
            100.0 * reused / (reused + scratch), reused + scratch, reused, scratch)))
        task_width = max(len(t) for t, _ in active)
        for t, (no_sstate, sstate) in active:
            total = len(sstate) + len(no_sstate)
            rows.append(('', '%-*s  %5.1f%%  (%d setscene, %d scratch)' % (
                task_width, t, 100.0 * len(sstate) / total, len(sstate), len(no_sstate))))

    if failed:
        rows.append(('Failed tasks', ', '.join('%s %s' % (pf, t) for pf, t in failed)))

    slowest.sort(reverse=True)
    slowest = slowest[:slowest_n]
    if slowest:
        name_width = max(len(pf) for _, pf, _ in slowest)
        time_width = max(len(_oea_bs_time(s)) for s, _, _ in slowest)
        for n, (elapsed, pf, t) in enumerate(slowest):
            rows.append(('Slowest tasks' if n == 0 else '', '%-*s  %-*s  %s' % (
                time_width, _oea_bs_time(elapsed), name_width, pf, t)))

    width = max(len(label) for label, _ in rows) if rows else 0
    head = 'Build summary'
    mb = machinebuild or machine
    if mb:
        head += ' for %s' % mb
        if machine and machinebuild and machine != machinebuild:
            head += ' (%s)' % machine
    lt = time.localtime(now)
    off = time.strftime('%z', lt)
    off = '(%s:%s)' % (off[:3], off[3:]) if off else ''
    ts = ' '.join(p for p in (time.strftime('%Y-%m-%d %H:%M:%S', lt), off) if p)
    head += ' at %s:' % ts
    yield ''
    yield head
    for label, text in rows:
        yield '  %-*s  %s' % (width, label, text)

python oea_buildsummary_start() {
    import json, os
    counters = _oea_bs_ccache(e.data)
    bsdir = _oea_bs_dir(e.data)
    if counters is None or not bsdir:
        return
    try:
        os.makedirs(bsdir, exist_ok=True)
        with open(os.path.join(bsdir, 'ccache_start'), 'w') as f:
            json.dump(counters, f)
    except Exception:
        pass
}
addhandler oea_buildsummary_start
oea_buildsummary_start[eventmask] = "bb.event.BuildStarted"

python oea_buildsummary() {
    import json, os
    bsdir = _oea_bs_dir(e.data)
    if not bsdir or not os.path.isdir(bsdir):
        return
    cc_start = None
    try:
        with open(os.path.join(bsdir, 'ccache_start')) as f:
            cc_start = json.load(f)
    except Exception:
        pass
    try:
        slowest_n = int(e.data.getVar('OEA_BUILDSUMMARY_SLOWEST') or 10)
    except ValueError:
        slowest_n = 10
    for line in _oea_bs_report(bsdir, (e.data.getVar('SSTATETASKS') or '').split(),
                               slowest_n, cc_start, _oea_bs_ccache(e.data),
                               e.data.getVar('MACHINE'), e.data.getVar('MACHINEBUILD')):
        bb.plain(line)
}
addhandler oea_buildsummary
oea_buildsummary[eventmask] = "bb.event.BuildCompleted"
