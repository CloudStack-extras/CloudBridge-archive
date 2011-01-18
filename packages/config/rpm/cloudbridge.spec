%define __os_install_post %{nil}
%global debug_package %{nil}

%define _ver 2.1.97
%define _rel 1

Name:      cloud-bridge
Summary:   Cloud.com Bridge 
Version:   %{_ver}
#http://fedoraproject.org/wiki/PackageNamingGuidelines#Pre-Release_packages
%if "%{?_prerelease}" != ""
Release:   0.%{_build_number}%{_prerelease}
%else
Release:   %{_rel}
%endif
License:   GPLv3+ with exceptions or CSL 1.1
Vendor:    Cloud.com, Inc. <sqa@cloud.com>
Packager:  Cloud.com <cloud@cloud.com>
Source0:   cloud-bridge-%{_ver}.tar.bz2
Group:     System Environment/Libraries
Requires: java >= 1.6.0
Requires: tomcat6
Obsoletes: cloud-bridge < %{version}-%{release}

%description
This is the Cloud.com Bridge

%prep

%setup -q -n %{name}-%{_ver}

%build

%define _localstatedir /var
%define _sharedstatedir /usr/share/
./waf configure --prefix=%{_prefix} --libdir=%{_libdir} --bindir=%{_bindir} --javadir=%{_javadir} --sharedstatedir=%{_sharedstatedir} --localstatedir=%{_localstatedir} --sysconfdir=%{_sysconfdir} --mandir=%{_mandir} --docdir=%{_docdir}/%{name}-%{version} --fast

%install
[ ${RPM_BUILD_ROOT} != "/" ] && rm -rf ${RPM_BUILD_ROOT}
ant deploy-rpm-install

%clean

#[ ${RPM_BUILD_ROOT} != "/" ] && rm -rf ${RPM_BUILD_ROOT}


%preun 
/sbin/service cloud-bridge stop || true
if [ "$1" == "0" ] ; then
    /sbin/chkconfig --del cloud-bridge  > /dev/null 2>&1 || true
    /sbin/service cloud-bridge stop > /dev/null 2>&1 || true
fi

%pre 
id cloud > /dev/null 2>&1 || /usr/sbin/useradd -M -c "Cloud.com Bridge unprivileged user" \
     -r -s /bin/sh -d %{_sharedstatedir}/cloud cloud|| true
# user harcoded here

%post 
if [ "$1" == "1" ] ; then
    /sbin/chkconfig --add cloud-bridge > /dev/null 2>&1 || true
    /sbin/chkconfig --level 345 cloud-bridge on > /dev/null 2>&1 || true
fi

%files 
%defattr(0644,cloud,cloud,0755)
/usr/share/cloud/bridge/conf/*
/usr/share/cloud/bridge/lib/*
/usr/share/cloud/bridge/webapps/*
%attr(0644,root,root) /usr/share/cloud/setup/bridge/db/*
%attr(0755,root,root) /etc/init.d/cloud-bridge
%attr(0755,root,root) /usr/bin/cloud-setup-bridge
%attr(0755,root,root) /usr/bin/cloud-setup-bridge-db
