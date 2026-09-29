import React from 'react';
import { Search, Calendar, User, Clock, CheckCircle, Users, ClipboardList, UserPlus, ShieldAlert } from 'lucide-react';

// --- PATIENT DASHBOARD ---
export const PatientDashboard: React.FC = () => {
  const doctors = [
    { name: 'Dr. Sarah Johnson', spec: 'Cardiologist', rating: '4.9', img: 'https://images.unsplash.com/photo-1559839734-2b71f1e3c77d?auto=format&fit=crop&q=80&w=150' },
    { name: 'Dr. Michael Chen', spec: 'Neurologist', rating: '4.8', img: 'https://images.unsplash.com/photo-1612349317150-e413f6a5b16d?auto=format&fit=crop&q=80&w=150' },
    { name: 'Dr. Emily White', spec: 'Dermatologist', rating: '5.0', img: 'https://images.unsplash.com/photo-1594824476967-48c8b964273f?auto=format&fit=crop&q=80&w=150' },
  ];

  return (
    <div className="space-y-10">
      <section>
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-2xl font-bold text-slate-dark">Find a Doctor</h2>
          <div className="relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={18} />
            <input placeholder="Search specialty..." className="pl-10 pr-4 py-2 bg-white border border-gray-100 rounded-xl text-sm outline-none focus:ring-2 focus:ring-blue-500/10 transition-all" />
          </div>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {doctors.map((doc, i) => (
            <div key={i} className="bg-white p-6 rounded-[32px] border border-gray-50 shadow-sm hover:shadow-md transition-all group">
              <img src={doc.img} className="w-16 h-16 rounded-2xl object-cover mb-4 ring-4 ring-gray-50 group-hover:ring-blue-50" alt={doc.name} />
              <h3 className="font-bold text-slate-dark">{doc.name}</h3>
              <p className="text-xs text-gray-400 font-medium mb-4">{doc.spec}</p>
              <div className="flex items-center justify-between">
                <span className="text-xs font-bold text-blue-600 bg-blue-50 px-2 py-1 rounded-lg">⭐ {doc.rating}</span>
                <button className="text-xs font-bold bg-slate-dark text-white px-4 py-2 rounded-full hover:bg-blue-600 transition-colors">Book Appointment</button>
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
};

// --- DOCTOR DASHBOARD ---
export const DoctorDashboard: React.FC = () => {
  const appointments = [
    { patient: 'Alice Smith', time: '09:00 AM', type: 'Checkup', status: 'Pending' },
    { patient: 'Bob Wilson', time: '10:30 AM', type: 'Follow-up', status: 'In-Progress' },
  ];

  return (
    <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
      <div className="lg:col-span-2 space-y-8">
        <section className="bg-white p-8 rounded-[32px] border border-gray-50 shadow-sm">
          <h2 className="text-xl font-bold text-slate-dark mb-6 flex items-center gap-2">
            <Clock className="text-blue-500" size={20} /> Today's Appointments
          </h2>
          <div className="space-y-4">
            {appointments.map((ap, i) => (
              <div key={i} className="flex items-center justify-between p-4 rounded-2xl bg-gray-50 border border-transparent hover:border-blue-100 transition-all cursor-pointer">
                <div className="flex items-center gap-4">
                  <div className="w-10 h-10 rounded-full bg-white flex items-center justify-center font-bold text-blue-600 shadow-sm">{ap.patient.charAt(0)}</div>
                  <div>
                    <p className="text-sm font-bold text-slate-dark">{ap.patient}</p>
                    <p className="text-xs text-gray-400">{ap.type}</p>
                  </div>
                </div>
                <div className="text-right">
                  <p className="text-sm font-bold text-slate-dark">{ap.time}</p>
                  <span className="text-[10px] uppercase tracking-widest font-bold text-blue-500">{ap.status}</span>
                </div>
              </div>
            ))}
          </div>
        </section>
      </div>

      <div className="space-y-8">
        <section className="bg-white p-8 rounded-[32px] border border-gray-50 shadow-sm">
          <h2 className="text-xl font-bold text-slate-dark mb-6 flex items-center gap-2">
            <Users className="text-blue-500" size={20} /> My Patients
          </h2>
          <div className="space-y-4">
            {['John Doe', 'Emma Watson', 'Chris Evans'].map((name, i) => (
              <div key={i} className="flex items-center justify-between group">
                <div className="flex items-center gap-3">
                  <div className="w-8 h-8 rounded-lg bg-blue-50 text-blue-600 flex items-center justify-center text-xs font-bold">{name.charAt(0)}</div>
                  <span className="text-sm font-medium text-gray-600 group-hover:text-slate-dark transition-colors">{name}</span>
                </div>
                <button className="text-[10px] font-bold text-blue-600 hover:underline">View Records</button>
              </div>
            ))}
          </div>
        </section>
      </div>
    </div>
  );
};

// --- ADMIN DASHBOARD ---
export const AdminDashboard: React.FC = () => {
  return (
    <div className="space-y-10">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {[
          { label: 'Total Doctors', val: '124', icon: <UserPlus className="text-blue-600" /> },
          { label: 'Total Patients', val: '1,842', icon: <Users className="text-teal-600" /> },
          { label: 'Pending Approvals', val: '12', icon: <ShieldAlert className="text-amber-600" /> },
        ].map((s, i) => (
          <div key={i} className="bg-white p-8 rounded-[32px] border border-gray-50 shadow-sm">
            <div className="mb-4">{s.icon}</div>
            <p className="text-sm text-gray-400 font-medium">{s.label}</p>
            <p className="text-3xl font-bold text-slate-dark">{s.val}</p>
          </div>
        ))}
      </div>

      <section className="bg-white p-8 rounded-[32px] border border-gray-50 shadow-sm">
        <h2 className="text-xl font-bold text-slate-dark mb-6">Manage Doctor Registrations</h2>
        <div className="overflow-x-auto">
          <table className="w-full text-left">
            <thead>
              <tr className="border-b border-gray-50">
                <th className="pb-4 text-xs font-bold text-gray-400 uppercase tracking-widest">Doctor Name</th>
                <th className="pb-4 text-xs font-bold text-gray-400 uppercase tracking-widest">Specialization</th>
                <th className="pb-4 text-xs font-bold text-gray-400 uppercase tracking-widest">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-50">
              {[
                { name: 'Dr. James Bond', spec: 'General Physician' },
                { name: 'Dr. Bruce Wayne', spec: 'Orthopedic' },
              ].map((d, i) => (
                <tr key={i}>
                  <td className="py-4 text-sm font-bold text-slate-dark">{d.name}</td>
                  <td className="py-4 text-sm text-gray-500">{d.spec}</td>
                  <td className="py-4">
                    <button className="text-xs font-bold text-white bg-blue-600 px-4 py-2 rounded-xl hover:bg-blue-700 transition-colors">Approve</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </div>
  );
};
