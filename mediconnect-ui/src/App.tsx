import { useState } from 'react';
import { 
  Activity, Users, UserRound, ShieldCheck, Search, 
  Clock, Calendar, UserPlus, ShieldAlert, LogOut,
  ChevronRight, Star
} from 'lucide-react';

// --- TYPES ---
type Role = 'Patient' | 'Doctor' | 'Admin';

// --- SUB-COMPONENTS ---

const Sidebar = ({ currentRole, setCurrentRole, signature }: { currentRole: Role, setCurrentRole: (r: Role) => void, signature: string }) => {
  const roles: { id: Role; icon: any; label: string }[] = [
    { id: 'Patient', icon: <UserRound size={20} />, label: 'Patient Portal' },
    { id: 'Doctor', icon: <Activity size={20} />, label: 'Doctor Panel' },
    { id: 'Admin', icon: <ShieldCheck size={20} />, label: 'Admin Console' },
  ];

  return (
    <aside className="fixed left-0 top-0 h-screen w-64 bg-white border-r border-gray-100 flex flex-col z-50 shadow-sm">
      <div className="p-8 flex items-center gap-3">
        <div className="w-10 h-10 bg-blue-600 rounded-2xl flex items-center justify-center text-white shadow-lg shadow-blue-100">
          <Activity size={24} />
        </div>
        <span className="font-bold text-xl text-slate-900 tracking-tight">MediConnect</span>
      </div>

      <nav className="flex-1 px-4 mt-8">
        <p className="text-[10px] font-bold text-gray-400 uppercase tracking-[0.2em] mb-6 px-4">Dashboards</p>
        <ul className="space-y-3">
          {roles.map((role) => (
            <li key={role.id}>
              <button
                onClick={() => setCurrentRole(role.id)}
                className={`w-full flex items-center gap-4 p-4 rounded-2xl transition-all duration-300 ${
                  currentRole === role.id 
                    ? 'bg-blue-50 text-blue-600 shadow-sm shadow-blue-50' 
                    : 'text-gray-500 hover:bg-gray-50 hover:text-gray-700'
                }`}
              >
                <span className={currentRole === role.id ? 'text-blue-600' : 'text-gray-400'}>
                  {role.icon}
                </span>
                <span className="font-bold text-sm">{role.label}</span>
              </button>
            </li>
          ))}
        </ul>
      </nav>

      <div className="p-8 mt-auto border-t border-gray-50 flex flex-col items-center">
        <p className="text-[10px] text-gray-300 font-bold uppercase tracking-[0.2em]">
          {signature}
        </p>
      </div>
    </aside>
  );
};

const PatientView = () => (
  <div className="animate-in fade-in slide-in-from-bottom-4 duration-500">
    <div className="flex items-center justify-between mb-10">
      <div>
        <h2 className="text-3xl font-extrabold text-slate-900">Find Your Specialist</h2>
        <p className="text-gray-400 font-medium mt-1">Book an appointment with our top-rated doctors.</p>
      </div>
      <div className="relative group">
        <Search className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400 group-focus-within:text-blue-500 transition-colors" size={18} />
        <input placeholder="Search specialty..." className="pl-12 pr-6 py-3 bg-white border border-gray-100 rounded-2xl text-sm outline-none focus:ring-4 focus:ring-blue-500/5 transition-all w-64 shadow-sm" />
      </div>
    </div>
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
      {[
        { name: 'Dr. Sarah Johnson', spec: 'Cardiologist', rate: '4.9', img: 'https://images.unsplash.com/photo-1559839734-2b71f1e3c77d?w=150&h=150&fit=crop' },
        { name: 'Dr. Michael Chen', spec: 'Neurologist', rate: '4.8', img: 'https://images.unsplash.com/photo-1612349317150-e413f6a5b16d?w=150&h=150&fit=crop' },
        { name: 'Dr. Emily White', spec: 'Dermatologist', rate: '5.0', img: 'https://images.unsplash.com/photo-1594824476967-48c8b964273f?w=150&h=150&fit=crop' },
      ].map((doc, i) => (
        <div key={i} className="bg-white p-8 rounded-[40px] border border-gray-50 shadow-sm hover:shadow-xl hover:-translate-y-1 transition-all group">
          <img src={doc.img} className="w-20 h-20 rounded-[24px] object-cover mb-6 ring-4 ring-gray-50 group-hover:ring-blue-50 transition-all" alt={doc.name} />
          <h3 className="font-bold text-xl text-slate-900">{doc.name}</h3>
          <p className="text-sm text-gray-400 font-bold mt-1 mb-6 uppercase tracking-wider">{doc.spec}</p>
          <div className="flex items-center justify-between pt-6 border-t border-gray-50">
            <span className="flex items-center gap-1 text-sm font-black text-blue-600 bg-blue-50 px-3 py-1.5 rounded-xl"><Star size={14} fill="currentColor"/> {doc.rate}</span>
            <button className="text-sm font-bold bg-slate-900 text-white px-6 py-3 rounded-2xl hover:bg-blue-600 transition-colors flex items-center gap-2 group/btn">
              Book <ChevronRight size={16} className="group-hover/btn:translate-x-1 transition-transform" />
            </button>
          </div>
        </div>
      ))}
    </div>
  </div>
);

const DoctorView = () => (
  <div className="grid grid-cols-1 lg:grid-cols-3 gap-10 animate-in fade-in slide-in-from-bottom-4 duration-500">
    <div className="lg:col-span-2 space-y-10">
      <section className="bg-white p-10 rounded-[48px] border border-gray-50 shadow-sm">
        <h2 className="text-2xl font-extrabold text-slate-900 mb-8 flex items-center gap-3">
          <Clock className="text-blue-500" size={28} /> Today's Appointments
        </h2>
        <div className="space-y-4">
          {[
            { patient: 'Alice Smith', time: '09:00 AM', type: 'Annual Checkup', status: 'Pending' },
            { patient: 'Bob Wilson', time: '10:30 AM', type: 'Heart Follow-up', status: 'In-Progress' },
            { patient: 'Clara Oswald', time: '01:15 PM', type: 'General Consultation', status: 'Scheduled' },
          ].map((ap, i) => (
            <div key={i} className="flex items-center justify-between p-6 rounded-[28px] bg-gray-50/50 border border-transparent hover:border-blue-100 hover:bg-white transition-all cursor-pointer group">
              <div className="flex items-center gap-5">
                <div className="w-12 h-12 rounded-2xl bg-white shadow-sm flex items-center justify-center font-black text-blue-600 group-hover:bg-blue-600 group-hover:text-white transition-all">{ap.patient.charAt(0)}</div>
                <div>
                  <p className="font-bold text-slate-900">{ap.patient}</p>
                  <p className="text-xs text-gray-400 font-bold uppercase tracking-widest">{ap.type}</p>
                </div>
              </div>
              <div className="text-right">
                <p className="font-black text-slate-900">{ap.time}</p>
                <span className="text-[10px] uppercase tracking-widest font-black text-blue-500">{ap.status}</span>
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
    <div className="space-y-10">
      <section className="bg-white p-10 rounded-[48px] border border-gray-50 shadow-sm">
        <h2 className="text-2xl font-extrabold text-slate-900 mb-8 flex items-center gap-3">
          <Users className="text-blue-500" size={28} /> My Patients
        </h2>
        <div className="space-y-6">
          {['John Doe', 'Emma Watson', 'Chris Evans', 'Tony Stark'].map((name, i) => (
            <div key={i} className="flex items-center justify-between group cursor-pointer">
              <div className="flex items-center gap-4">
                <div className="w-10 h-10 rounded-xl bg-blue-50 text-blue-600 flex items-center justify-center text-xs font-black group-hover:bg-blue-600 group-hover:text-white transition-all">{name.charAt(0)}</div>
                <span className="text-sm font-bold text-gray-600 group-hover:text-slate-900 transition-colors">{name}</span>
              </div>
              <button className="text-xs font-black text-blue-600 hover:underline">RECORDS</button>
            </div>
          ))}
        </div>
      </section>
    </div>
  </div>
);

const AdminView = () => (
  <div className="space-y-12 animate-in fade-in slide-in-from-bottom-4 duration-500">
    <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
      {[
        { label: 'Total Doctors', val: '124', icon: <UserPlus className="text-blue-600" />, bg: 'bg-blue-50' },
        { label: 'Total Patients', val: '1,842', icon: <Users className="text-teal-600" />, bg: 'bg-teal-50' },
        { label: 'Pending Approvals', val: '12', icon: <ShieldAlert className="text-rose-600" />, bg: 'bg-rose-50' },
      ].map((s, i) => (
        <div key={i} className="bg-white p-10 rounded-[48px] border border-gray-50 shadow-sm flex flex-col">
          <div className={`w-14 h-14 ${s.bg} rounded-2xl flex items-center justify-center mb-6`}>{s.icon}</div>
          <p className="text-sm text-gray-400 font-bold uppercase tracking-widest mb-1">{s.label}</p>
          <p className="text-4xl font-black text-slate-900">{s.val}</p>
        </div>
      ))}
    </div>

    <section className="bg-white p-12 rounded-[48px] border border-gray-50 shadow-sm overflow-hidden">
      <h2 className="text-2xl font-extrabold text-slate-900 mb-10">Doctor Registration Requests</h2>
      <div className="w-full overflow-x-auto">
        <table className="w-full text-left">
          <thead>
            <tr className="border-b border-gray-50">
              <th className="pb-6 text-[10px] font-black text-gray-400 uppercase tracking-[0.2em]">Doctor Name</th>
              <th className="pb-6 text-[10px] font-black text-gray-400 uppercase tracking-[0.2em]">Specialization</th>
              <th className="pb-6 text-right text-[10px] font-black text-gray-400 uppercase tracking-[0.2em]">Action</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-50">
            {[
              { name: 'Dr. James Bond', spec: 'General Physician' },
              { name: 'Dr. Bruce Wayne', spec: 'Orthopedic' },
              { name: 'Dr. Diana Prince', spec: 'Ophthalmologist' },
            ].map((d, i) => (
              <tr key={i} className="group">
                <td className="py-6 font-bold text-slate-900">{d.name}</td>
                <td className="py-6 text-sm text-gray-500 font-medium">{d.spec}</td>
                <td className="py-6 text-right">
                  <button className="text-xs font-black text-white bg-blue-600 px-6 py-3 rounded-2xl hover:bg-blue-700 shadow-lg shadow-blue-100 transition-all">APPROVE</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </section>
  </div>
);

// --- MAIN APP ---

export default function App() {
  const [currentRole, setCurrentRole] = useState<Role>('Patient');

  return (
    <div className="min-h-screen bg-[#F9FAFB] font-sans selection:bg-blue-100">
      <Sidebar 
        currentRole={currentRole} 
        setCurrentRole={setCurrentRole} 
        signature="✨ Crafted by Vikash Kumar"
      />
      
      <main className="pl-64 transition-all duration-300">
        <header className="h-24 px-12 flex items-center justify-between sticky top-0 bg-[#F9FAFB]/80 backdrop-blur-md z-40">
          <div className="flex items-center gap-2">
            <span className="text-sm font-bold text-gray-400">Portal</span>
            <ChevronRight size={14} className="text-gray-300" />
            <span className="text-sm font-black text-blue-600 uppercase tracking-widest">{currentRole} Dashboard</span>
          </div>
          <div className="flex items-center gap-6">
            <div className="text-right">
              <p className="text-sm font-black text-slate-900">Vikash Kumar</p>
              <p className="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Premium Account</p>
            </div>
            <div className="w-12 h-12 rounded-2xl bg-gradient-to-tr from-blue-600 to-indigo-500 border-4 border-white shadow-xl flex items-center justify-center text-white font-black">
              VK
            </div>
          </div>
        </header>

        <div className="px-12 pb-20 max-w-7xl mx-auto">
          {currentRole === 'Patient' && <PatientView />}
          {currentRole === 'Doctor' && <DoctorView />}
          {currentRole === 'Admin' && <AdminView />}
        </div>
      </main>
    </div>
  );
}
