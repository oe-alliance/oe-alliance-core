#! /usr/bin/perl
#
# update-rc.d	Update the links in /etc/rc[0-9S].d/
#

use strict;
use warnings;
# NB: All Perl modules used here must be in perl-base. Specifically, depending
# on modules in perl-modules is not okay! See bug #716923

my $initd = "/etc/init.d";
my $etcd  = "/etc/rc";
my $root  = "";
my $notreally = 0;

# Print usage message and die.

sub usage {
	print STDERR "update-rc.d: error: @_\n" if ($#_ >= 0);
	print STDERR <<EOF;
usage: update-rc.d [-n] [-f] [-r <root>] <basename> remove
       update-rc.d [-n] [-r <root>] <basename> disable|enable [S|2|3|4|5]
		-n: not really
		-f: force
		-r: alternate root path (default is /)

The disable|enable API is not stable and might change in the future.
EOF
	exit (1);
}

exit insserv_updatercd(@ARGV);

sub save_last_action {
    # No-op (archive removed)
}

sub remove_last_action {
    # No-op (archive removed)
}

sub info {
    print STDOUT "update-rc.d: @_\n";
}

sub warning {
    print STDERR "update-rc.d: warning: @_\n";
}

sub error {
    print STDERR "update-rc.d: error: @_\n";
    exit (1);
}

sub error_code {
    my $rc = shift;
    print STDERR "update-rc.d: error: @_\n";
    exit ($rc);
}

sub make_path {
    my ($path) = @_;
    my @dirs = ();
    my @path = split /\//, $path;
    map { push @dirs, $_; mkdir join('/', @dirs), 0755; } @path;
}

sub systemd_reload {
    if ($root eq "") {
        if (-d "/run/systemd/system") {
            system("systemctl", "daemon-reload");
        }
    } else {
        if (-d "$root/run/systemd/system") {
            system("systemctl", "--root=$root", "daemon-reload");
        }
    }
}

# Creates the necessary links to enable/disable the service (equivalent of an
# initscript) in systemd.
sub make_systemd_links {
    my ($scriptname, $action) = @_;

    # In addition to the insserv call we also enable/disable the service
    # for systemd by creating the appropriate symlink in case there is a
    # native systemd service. We need to do this on our own instead of
    # using systemctl because systemd might not even be installed yet.
    my $service_path;
    if (-f "$root/etc/systemd/system/$scriptname.service") {
        $service_path = "/etc/systemd/system/$scriptname.service";
    } elsif (-f "$root/lib/systemd/system/$scriptname.service") {
        $service_path = "/lib/systemd/system/$scriptname.service";
    }
    if (defined($service_path)) {
        my $changed_sth;
        open my $fh, '<', $root . $service_path or error("unable to read $root$service_path");
        while (<$fh>) {
            chomp;
            if (/^\s*WantedBy=(.+)$/i) {
                my $wants_dir = "/etc/systemd/system/$1.wants";
                my $service_link = "$wants_dir/$scriptname.service";
                if ("enable" eq $action) {
                    make_path($root . $wants_dir);
                    symlink($service_path, $root . $service_link);
                } else {
                    unlink($root . $service_link) if -e $root . $service_link;
                }
            }
        }
        close($fh);
    }
}

# Manage the .override file for upstart jobs, so update-rc.d enable/disable
# work on upstart systems the same as on sysvinit/systemd.
sub upstart_toggle {
    my ($scriptname, $action) = @_;

    # This needs to be done by manually parsing .override files instead of
    # using initctl, because upstart might not be installed yet.
    my $service_path;
    if (-f "$root/etc/init/$scriptname.conf") {
        $service_path = "/etc/init/$scriptname.override";
    }
    if (!defined($service_path)) {
        return;
    }
    my $fh;
    my $enabled = 1;
    my $overrides = '';
    if (open $fh, '<', $root. $service_path ) {
        while (<$fh>) {
           if (/^\s*manual\s*$/) {
               $enabled = 0;
           } else {
               $overrides .= $_;
           }
        }
    }
    close($fh);

    if ($enabled && $action eq 'disable') {
        open $fh, '>>', $root . $service_path or error("unable to write $root$service_path");
        print $fh "manual\n";
        close($fh);
    } elsif (!$enabled && $action eq 'enable') {
        if ($overrides ne '') {
            open $fh, '>', $root . $service_path . '.new' or error ("unable to write $root$service_path");
            print $fh $overrides;
            close($fh);
            rename($root . $service_path . '.new', $root . $service_path) or error($!);
        } else {
            unlink($root . $service_path) or error($!);
        }
    }
}

## Dependency based
sub insserv_updatercd {
    my @args = @_;
    my @opts;
    my $scriptname;
    my $action;
    my $notreally = 0;

    my @orig_argv = @args;

    while($#args >= 0 && ($_ = $args[0]) =~ /^-/) {
        shift @args;
        if (/^-n$/) { push(@opts, $_); $notreally++; next }
        if (/^-f$/) { push(@opts, $_); next }
        if (/^-r$/) { $root=shift @args; $root =~ s/^\s+|\s+$//g; next }
        if (/^-h|--help$/) { &usage; }
        usage("unknown option");
    }

    usage("not enough arguments") if ($#args < 1);

    $scriptname = shift @args;
    $action = shift @args;
    my $insserv = "/usr/lib/insserv/insserv";
    # Fallback for older insserv package versions [2014-04-16]
    $insserv = "/sbin/insserv" if ( -x "/sbin/insserv");
    my @params = ();
    @params = ("-p", "$root/etc/init.d", "-o", "$root/etc/insserv/overrides", "-c", "$root/etc/insserv.conf") unless ($root eq "");
    my @tempopts=@opts;
    push(@tempopts, @params);
    
    if ("remove" eq $action) {
        if ( -f "$root/etc/init.d/$scriptname" ) {
            my $rc = system($insserv, @tempopts, "-r", $scriptname) >> 8;
            if (0 == $rc && !$notreally) {
                remove_last_action($scriptname);
            }
            error_code($rc, "insserv rejected the script header") if $rc;
            systemd_reload;
            exit $rc;
        } else {
            # insserv removes all dangling symlinks, no need to tell it
            # what to look for.
            my $rc = system($insserv, @tempopts) >> 8;
            if (0 == $rc && !$notreally) {
                remove_last_action($scriptname);
            }
            error_code($rc, "insserv rejected the script header") if $rc;
            systemd_reload;
            exit $rc;
        }
    } elsif ("defaults" eq $action || "start" eq $action ||
             "stop" eq $action) {
        # All start/stop/defaults arguments are discarded so emit a
        # message if arguments have been given and are in conflict
        # with Default-Start/Default-Stop values of LSB comment.
        if ("start" eq $action || "stop" eq $action) {
            cmp_args_with_defaults($scriptname, $action, @args);
        }

        if ( -f "$root/etc/init.d/$scriptname" ) {
            my $rc = system($insserv, @tempopts, $scriptname) >> 8;
            if (0 == $rc && !$notreally) {
                save_last_action($scriptname, @orig_argv);
            }
            error_code($rc, "insserv rejected the script header") if $rc;
            systemd_reload;
            exit $rc;
        } else {
            error("initscript does not exist: $root/etc/init.d/$scriptname");
        }
    } elsif ("disable" eq $action || "enable" eq $action) {
        make_systemd_links($scriptname, $action);

        upstart_toggle($scriptname, $action);

        insserv_toggle($notreally, $action, $scriptname, @args);
        # Call insserv to resequence modified links
        my $rc = system($insserv, @tempopts, $scriptname) >> 8;
        if (0 == $rc && !$notreally) {
            save_last_action($scriptname, @orig_argv);
        }
        error_code($rc, "insserv rejected the script header") if $rc;
        systemd_reload;
        exit $rc;
    } else {
        usage();
    }
}

sub parse_def_start_stop {
    my $script = shift;
    my (%lsb, @def_start_lvls, @def_stop_lvls);

    open my $fh, '<', $script or error("unable to read $root$script");
    while (<$fh>) {
        chomp;
        if (m/^### BEGIN INIT INFO$/) {
            $lsb{'begin'}++;
        }
        elsif (m/^### END INIT INFO$/) {
            $lsb{'end'}++;
            last;
        }
        elsif ($lsb{'begin'} and not $lsb{'end'}) {
            if (m/^# Default-Start:\s*(\S?.*)$/) {
                @def_start_lvls = split(' ', $1);
            }
            if (m/^# Default-Stop:\s*(\S?.*)$/) {
                @def_stop_lvls = split(' ', $1);
            }
        }
    }
    close($fh);

    return (\@def_start_lvls, \@def_stop_lvls);
}

sub lsb_header_for_script {
    my $name = shift;

    foreach my $file ("/etc/insserv/overrides/$name", "/etc/init.d/$name",
                      "/usr/share/insserv/overrides/$name") {
        return $file if -s $root . $file;
    }

    error("cannot find a LSB script for $name");
}

sub cmp_args_with_defaults {
    my ($name, $act) = (shift, shift);
    my ($lsb_start_ref, $lsb_stop_ref, $arg_str, $lsb_str);
    my (@arg_start_lvls, @arg_stop_lvls, @lsb_start_lvls, @lsb_stop_lvls);

    ($lsb_start_ref, $lsb_stop_ref) = parse_def_start_stop("$root/etc/init.d/$name");
    @lsb_start_lvls = @$lsb_start_ref;
    @lsb_stop_lvls  = @$lsb_stop_ref;
    return if (!@lsb_start_lvls and !@lsb_stop_lvls);

    warning "start and stop actions are no longer supported; falling back to defaults";
    my $start = $act eq 'start' ? 1 : 0;
    my $stop = $act eq 'stop' ? 1 : 0;

    # The legacy part of this program passes arguments starting with
    # "start|stop NN x y z ." but the insserv part gives argument list
    # starting with sequence number (ie. strips off leading "start|stop")
    # Start processing arguments immediately after the first seq number.
    my $argi = $_[0] eq $act ? 2 : 1;

    while (defined $_[$argi]) {
        my $arg = $_[$argi];

        # Runlevels 0 and 6 are always stop runlevels
        if ($arg eq 0 or $arg eq 6) {
            $start = 0; $stop = 1;
        } elsif ($arg eq 'start') {
            $start = 1; $stop = 0; $argi++; next;
        } elsif ($arg eq 'stop') {
            $start = 0; $stop = 1; $argi++; next;
        } elsif ($arg eq '.') {
            next;
        }
        push(@arg_start_lvls, $arg) if $start;
        push(@arg_stop_lvls, $arg) if $stop;
    } continue {
        $argi++;
    }

    if ($#arg_start_lvls != $#lsb_start_lvls or
        join("\0", sort @arg_start_lvls) ne join("\0", sort @lsb_start_lvls)) {
        $arg_str = @arg_start_lvls ? "@arg_start_lvls" : "none";
        $lsb_str = @lsb_start_lvls ? "@lsb_start_lvls" : "none";
        warning "start runlevel arguments ($arg_str) do not match",
                "$name Default-Start values ($lsb_str)";
    }
    if ($#arg_stop_lvls != $#lsb_stop_lvls or
        join("\0", sort @arg_stop_lvls) ne join("\0", sort @lsb_stop_lvls)) {
        $arg_str = @arg_stop_lvls ? "@arg_stop_lvls" : "none";
        $lsb_str = @lsb_stop_lvls ? "@lsb_stop_lvls" : "none";
        warning "stop runlevel arguments ($arg_str) do not match",
                "$name Default-Stop values ($lsb_str)";
    }
}

sub insserv_toggle {
    my ($dryrun, $act, $name) = (shift, shift, shift);
    my (@toggle_lvls, $start_lvls, $stop_lvls, @symlinks);
    my $lsb_header = lsb_header_for_script($name);

    # Extra arguments to disable|enable action are runlevels. If none
    # given parse LSB info for Default-Start value.
    if ($#_ >= 0) {
        @toggle_lvls = @_;
    } else {
        ($start_lvls, $stop_lvls) = parse_def_start_stop($lsb_header);
        @toggle_lvls = @$start_lvls;
        if ($#toggle_lvls < 0) {
            error("$name Default-Start contains no runlevels, aborting.");
        }
    }

    # Find symlinks in rc.d directories. Refuse to modify links in runlevels
    # not used for normal system start sequence.
    for my $lvl (@toggle_lvls) {
        if ($lvl !~ /^[S2345]$/) {
            warning("$act action will have no effect on runlevel $lvl");
            next;
        }
        push(@symlinks, $_) for glob("$root/etc/rc$lvl.d/[SK][0-9][0-9]$name");
    }

    if (!@symlinks) {
        error("no runlevel symlinks to modify, aborting!");
    }

    # Toggle S/K bit of script symlink.
    for my $cur_lnk (@symlinks) {
        my $sk;
        my @new_lnk = split(//, $cur_lnk);

        if ("disable" eq $act) {
            $sk = rindex($cur_lnk, '/S') + 1;
            next if $sk < 1;
            $new_lnk[$sk] = 'K';
        } else {
            $sk = rindex($cur_lnk, '/K') + 1;
            next if $sk < 1;
            $new_lnk[$sk] = 'S';
        }

        if ($dryrun) {
            printf("rename(%s, %s)\n", $cur_lnk, join('', @new_lnk));
            next;
        }

        rename($cur_lnk, join('', @new_lnk)) or error($!);
    }
}

