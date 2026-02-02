Name:           openscale
Version:        3.0.2
Release:        1%{?dist}
Summary:        Open-source weight and body metrics tracker

License:        GPLv3+
URL:            https://github.com/oliexdev/openScale
Source0:        %{name}-%{version}.tar.gz

BuildArch:      noarch
BuildRequires:  java-21-openjdk-devel
BuildRequires:  gradle
Requires:       java-21-openjdk-headless
Requires:       bluez

%description
openScale is an open-source application for tracking weight and
body metrics with support for Bluetooth scales. Features include:
 - Track weight, BMI, body fat, muscle mass, and more
 - Support for multiple Bluetooth scales
 - Data visualization with charts
 - Import/export data
 - Multi-user support

%prep
%setup -q

%build
./gradlew :linux_app:installDist

%install
rm -rf %{buildroot}
mkdir -p %{buildroot}%{_libdir}/openscale
mkdir -p %{buildroot}%{_bindir}
cp -r linux_app/build/install/linux_app/* %{buildroot}%{_libdir}/openscale/
ln -s %{_libdir}/openscale/bin/linux_app %{buildroot}%{_bindir}/openscale

%files
%license LICENSE
%doc README.md
%{_libdir}/openscale
%{_bindir}/openscale

%changelog
* Sun Feb 02 2026 openScale Team <maintainer@openscale.org> - 3.0.2-1
- Initial Fedora package for openScale Linux
- Add Kotlin Multiplatform support for Linux desktop
- Support for Bluetooth scale connectivity
- Health metrics tracking and visualization
