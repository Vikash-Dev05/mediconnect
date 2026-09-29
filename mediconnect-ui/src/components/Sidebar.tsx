import React from 'react';
import { LayoutDashboard, Users, UserRound, ShieldCheck, Activity, LogOut } from 'lucide-react';

export type Role = 'Patient' | 'Doctor' | 'Admin';

interface SidebarProps {
  currentRole: Role;
  setCurrentRole: (role: Role) => void;
  collapsed: boolean;
  setCollapsed: (collapsed: boolean) => void;
}

const Sidebar: React.FC<SidebarProps> = ({ currentRole, setCurrentRole, collapsed, setCollapsed }) => {
  const roles: { id: Role; icon: React.ReactNode; label: string }[] = [
    { id: 'Patient', icon: <UserRound size={20} />, label: 'Patient Portal' },
    { id: 'Doctor', icon: <Activity size={20} />, label: 'Doctor Panel' },
    { id: 'Admin', icon: <ShieldCheck size={20} />, label: 'Admin Console' },
  ];

  return (
    <aside 
      className={`fixed left-0 top-0 h-screen bg-white border-r border-gray-100 transition-all duration-300 ease-in-out flex flex-col z-50 ${
        collapsed ? 'w-20' : 'w-64'
      }`}
    >
      <div className="p-6 flex items-center gap-3">
        <div className="w-8 h-8 bg-blue-600 rounded-lg flex items-center justify-center text-white shrink-0">
          <Activity size={20} />
        </div>
        {!collapsed && <span className="font-bold text-xl text-slate-dark tracking-tight">MediConnect</span>}
      </div>

      <nav className="flex-1 px-4 mt-6">
        <p className={`text-[10px] font-bold text-gray-400 uppercase tracking-widest mb-4 px-3 ${collapsed ? 'text-center' : ''}`}>
          {collapsed ? 'Roles' : 'Switch Dashboard'}
        </p>
        <ul className="space-y-2">
          {roles.map((role) => (
            <li key={role.id}>
              <button
                onClick={() => setCurrentRole(role.id)}
                className={`w-full flex items-center gap-4 p-3 rounded-xl transition-all duration-200 ${
                  currentRole === role.id 
                    ? 'bg-blue-50 text-blue-600 font-bold' 
                    : 'text-gray-500 hover:bg-gray-50 hover:text-gray-700'
                } ${collapsed ? 'justify-center' : ''}`}
              >
                <span className={`${currentRole === role.id ? 'text-blue-600' : 'text-gray-400'}`}>
                  {role.icon}
                </span>
                {!collapsed && <span>{role.label}</span>}
              </button>
            </li>
          ))}
        </ul>
      </nav>

      <div className="p-6 mt-auto border-t border-gray-50">
        {!collapsed && (
          <button className="flex items-center gap-3 text-gray-400 hover:text-red-500 transition-colors w-full p-2 mb-4">
            <LogOut size={18} />
            <span className="text-sm font-medium">Logout</span>
          </button>
        )}
        <div className="text-center">
          <p className={`text-[10px] text-gray-300 font-medium uppercase tracking-[0.2em] ${collapsed ? 'hidden' : 'block'}`}>
            ✨ Crafted by Vikash Kumar
          </p>
          {collapsed && <span className="text-gray-300 text-[10px]">✨ VK</span>}
        </div>
      </div>
    </aside>
  );
};

export default Sidebar;
