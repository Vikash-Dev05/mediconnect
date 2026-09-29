// =========================================
// GLOBAL VARIABLES & SETUP
// =========================================
let isEditingPatient = false;
let currentUserRole = ''; // Current user ka role store karne ke liye

document.addEventListener('DOMContentLoaded', async () => {
    // 1. Sabse pehle pata karo ki KAUN login hai
    try {
        const roleRes = await fetch('/api/auth/current-role');
        currentUserRole = await roleRes.text();
        console.log("Logged in as:", currentUserRole);
    } catch (e) {
        console.error("Error fetching role:", e);
    }

    // 2. Role ke hisaab se dashboard set karo
    setupDashboardByRole();

    // 3. Event Listeners setup karo
    document.getElementById('patientForm').addEventListener('submit', handlePatientFormSubmit);
    document.getElementById('patientCancelBtn').addEventListener('click', resetPatientForm);
    document.getElementById('doctorForm').addEventListener('submit', handleAddDoctor);
    document.getElementById('appointmentForm').addEventListener('submit', handleBookAppointment);
});

// =========================================
// SECTION 0: ROLE-BASED DASHBOARD SETUP (NEW!)
// =========================================
function setupDashboardByRole() {
    // Pehle sab kuch chupa do
    document.getElementById('nav-patient').style.display = 'none';
    document.getElementById('nav-doctor').style.display = 'none';
    document.getElementById('nav-appointment').style.display = 'none';

    if (currentUserRole === 'ROLE_ADMIN') {
        // ADMIN: Sab kuch dekh sakta hai
        document.getElementById('nav-patient').style.display = 'inline-block';
        document.getElementById('nav-doctor').style.display = 'inline-block';
        document.getElementById('nav-appointment').style.display = 'inline-block';
        showSection('patient'); // Default section for admin
    } 
    else if (currentUserRole === 'ROLE_PATIENT') {
        // PATIENT: Sirf Appointment tab dekh sakta hai
        document.getElementById('nav-appointment').style.display = 'inline-block';
        // Patient ko baaki patients ki list nahi dikhni chahiye (Future improvement)
        showSection('appointment');
    }
    else if (currentUserRole === 'ROLE_DOCTOR') {
        // DOCTOR: Sirf Appointment tab dekh sakta hai (filhal)
        document.getElementById('nav-appointment').style.display = 'inline-block';
        showSection('appointment');
    }
}

// =========================================
// SECTION 1: NAVIGATION LOGIC
// =========================================
function showSection(sectionName) {
    document.querySelectorAll('.module-section').forEach(sec => sec.style.display = 'none');
    document.querySelectorAll('.navbar button').forEach(btn => btn.classList.remove('active'));

    document.getElementById(`${sectionName}-section`).style.display = 'block';
    document.getElementById(`nav-${sectionName}`).classList.add('active');

    if (sectionName === 'patient') loadPatients();
    if (sectionName === 'doctor') { loadDoctors(); loadCategories(); }
    if (sectionName === 'appointment') { loadAppointments(); populateDropdowns(); }
}

// =========================================
// SECTION 2: PATIENT MODULE LOGIC
// =========================================
async function loadPatients() {
    const tableBody = document.getElementById('patientsTableBody');
    tableBody.innerHTML = '<tr><td colspan="5">Loading...</td></tr>';
    try {
        const patients = await fetchData('/api/patients/all');
        tableBody.innerHTML = '';
        patients.forEach(p => {
            const row = tableBody.insertRow();
            let actions = '';
            // Sirf ADMIN hi edit/delete kar sakta hai
            if (currentUserRole === 'ROLE_ADMIN') {
                actions = `
                    <button class="btn btn-primary" style="padding: 5px 10px; font-size: 14px;" onclick='editPatient(${JSON.stringify(p)})'>Edit</button>
                    <button class="btn btn-danger" onclick="deletePatient(${p.id})">Delete</button>
                `;
            }
            row.innerHTML = `<td>${p.id}</td><td>${p.name}</td><td>${p.age}</td><td>${p.gender}</td><td>${actions}</td>`;
        });
    } catch (e) { console.error(e); tableBody.innerHTML = '<tr><td colspan="5" style="color:red">Error loading data</td></tr>'; }
}

async function handlePatientFormSubmit(e) {
    e.preventDefault();
    const patient = {
        name: document.getElementById('patientName').value,
        age: document.getElementById('patientAge').value,
        gender: document.getElementById('patientGender').value
    };

    if (isEditingPatient) {
        const id = document.getElementById('patientId').value;
        await saveData(`/api/patients/update/${id}`, 'PUT', patient, 'Patient updated!');
        resetPatientForm();
    } else {
        await saveData('/api/patients/add', 'POST', patient, 'Patient added!');
        document.getElementById('patientForm').reset();
    }
    loadPatients();
}

function editPatient(patient) {
    isEditingPatient = true;
    document.getElementById('patientFormTitle').innerText = 'Update Patient';
    document.getElementById('patientSubmitBtn').innerText = 'Save Changes';
    document.getElementById('patientSubmitBtn').classList.remove('btn-success');
    document.getElementById('patientSubmitBtn').classList.add('btn-primary');
    document.getElementById('patientCancelBtn').style.display = 'inline-block';

    document.getElementById('patientId').value = patient.id;
    document.getElementById('patientName').value = patient.name;
    document.getElementById('patientAge').value = patient.age;
    document.getElementById('patientGender').value = patient.gender;
    window.scrollTo(0, 0);
}

function resetPatientForm() {
    isEditingPatient = false;
    document.getElementById('patientForm').reset();
    document.getElementById('patientFormTitle').innerText = 'Add New Patient';
    document.getElementById('patientSubmitBtn').innerText = 'Add Patient';
    document.getElementById('patientSubmitBtn').classList.remove('btn-primary');
    document.getElementById('patientSubmitBtn').classList.add('btn-success');
    document.getElementById('patientCancelBtn').style.display = 'none';
}

async function deletePatient(id) {
    if (confirm('Delete this patient?')) {
        await deleteData(`/api/patients/delete/${id}`);
        loadPatients();
    }
}

// =========================================
// SECTION 3: DOCTOR MODULE LOGIC
// =========================================
async function loadDoctors() {
    const tableBody = document.getElementById('doctorsTableBody');
    try {
        const doctors = await fetchData('/api/doctors/all');
        tableBody.innerHTML = '';
        doctors.forEach(d => {
            const row = tableBody.insertRow();
            const categoryName = d.category ? d.category.name : 'N/A';
            const timing = `${d.timingFrom || ''} - ${d.timingTo || ''}`;
            let actions = '';
            if (currentUserRole === 'ROLE_ADMIN') {
                actions = `<button class="btn btn-danger" onclick="deleteDoctor(${d.id})">Delete</button>`;
            }
            row.innerHTML = `<td>${d.id}</td><td>${d.name}</td><td>${categoryName}</td><td>${d.qualification}</td><td>${timing}</td><td>${actions}</td>`;
        });
    } catch (e) { console.error(e); }
}

async function handleAddDoctor(e) {
    e.preventDefault();
    const doctor = {
        name: document.getElementById('doctorName').value,
        categoryId: document.getElementById('doctorCategory').value,
        qualification: document.getElementById('doctorQualification').value,
        timingFrom: document.getElementById('doctorTimingFrom').value,
        timingTo: document.getElementById('doctorTimingTo').value
    };
    await saveData('/api/doctors/add', 'POST', doctor, 'Doctor added!');
    document.getElementById('doctorForm').reset();
    loadDoctors();
}

async function deleteDoctor(id) {
    if (confirm('Delete this doctor?')) {
        await deleteData(`/api/doctors/delete/${id}`);
        loadDoctors();
    }
}

// =========================================
// SECTION 4: CATEGORY & APPOINTMENT LOGIC
// =========================================
async function loadCategories() {
    try {
        const categories = await fetchData('/api/categories/all');
        const categorySelect = document.getElementById('doctorCategory');
        if (categorySelect) {
            categorySelect.innerHTML = '<option value="">Select Specialization</option>';
            categories.forEach(c => categorySelect.innerHTML += `<option value="${c.id}">${c.name}</option>`);
        }
    } catch (e) { console.error(e); }
}

async function loadAppointments() {
    const tableBody = document.getElementById('appointmentsTableBody');
    try {
        const appointments = await fetchData('/api/appointments/all');
        tableBody.innerHTML = '';
        appointments.forEach(app => {
            const row = tableBody.insertRow();
            row.innerHTML = `<td>${app.id}</td><td>${app.patient ? app.patient.name : 'N/A'}</td><td>${app.doctor ? app.doctor.name : 'N/A'}</td><td>${app.appointmentDate}</td>
                <td><span style="background:#28a745; color:white; padding:3px 8px; border-radius:10px; font-size:12px">${app.status}</span></td>`;
        });
    } catch (e) { console.error(e); }
}

async function handleBookAppointment(e) {
    e.preventDefault();
    const bookingData = {
        patientId: document.getElementById('appointmentPatient').value,
        doctorId: document.getElementById('appointmentDoctor').value,
        date: document.getElementById('appointmentDate').value
    };
    await saveData('/api/appointments/book', 'POST', bookingData, 'Appointment Booked Successfully!');
    document.getElementById('appointmentForm').reset();
    loadAppointments();
}

async function populateDropdowns() {
    try {
        const [patients, doctors] = await Promise.all([
            fetchData('/api/patients/all'),
            fetchData('/api/doctors/all')
        ]);
        const pSelect = document.getElementById('appointmentPatient');
        const dSelect = document.getElementById('appointmentDoctor');
        pSelect.innerHTML = '<option value="">Select Patient</option>';
        patients.forEach(p => pSelect.innerHTML += `<option value="${p.id}">${p.name} (ID: ${p.id})</option>`);
        dSelect.innerHTML = '<option value="">Select Doctor</option>';
        doctors.forEach(d => dSelect.innerHTML += `<option value="${d.id}">${d.name} - ${d.category ? d.category.name : ''}</option>`);
    } catch (e) { console.error(e); }
}

// =========================================
// HELPER FUNCTIONS
// =========================================
async function fetchData(url) {
    const res = await fetch(url); // Browser khud session cookie bhejta hai, Auth header ki zaroorat nahi
    if (!res.ok) throw new Error(`HTTP Error ${res.status}`);
    return await res.json();
}

async function saveData(url, method, data, msg) {
    try {
        const res = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error(await res.text());
        alert(msg);
    } catch (e) { alert('Error: ' + e.message); }
}

async function deleteData(url) {
    try {
        const res = await fetch(url, { method: 'DELETE' });
        if (!res.ok) throw new Error(await res.text());
    } catch (e) { alert('Error: ' + e.message); }
}