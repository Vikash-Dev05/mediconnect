import React from 'react';
import { Search, Bell, Menu } from 'lucide-react';

interface HeaderProps {
  onMenuClick?: () => void;
}

const Header: React.FC<HeaderProps> = ({ onMenuClick }) => {
  return (
    <header className="h-20 bg-white border-b border-gray-100 flex items-center justify-between px-8 sticky top-0 z-40">
      <div className="flex items-center flex-1 max-w-xl">
        <button 
          onClick={onMenuClick}
          className="lg:hidden mr-4 p-2 text-gray-400 hover:text-primary transition-colors"
        >
          <Menu size={24} />
        </button>
        <div className="relative w-full group">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 group-focus-within:text-primary transition-colors" size={18} />
          <input 
            type="text" 
            placeholder="Search patients, doctors, or records..."
            className="w-full bg-gray-50 border-none rounded-2xl py-2.5 pl-11 pr-4 text-sm focus:ring-2 focus:ring-primary/20 transition-all placeholder:text-gray-400 outline-none"
          />
        </div>
      </div>

      <div className="flex items-center gap-6">
        <button className="relative text-gray-400 hover:text-primary transition-colors p-2">
          <Bell size={20} />
          <span className="absolute top-2 right-2 w-2 h-2 bg-red-500 rounded-full border-2 border-white"></span>
        </button>
        
        <div className="flex items-center gap-3 pl-6 border-l border-gray-100">
          <div className="text-right hidden sm:block">
            <p className="text-sm font-semibold text-slate-dark">Dr. Sarah Johnson</p>
            <p className="text-[11px] text-gray-400 font-medium">Head Surgeon</p>
          </div>
          <div className="w-10 h-10 rounded-full bg-gradient-to-tr from-primary to-teal-400 p-[2px] cursor-pointer hover:scale-105 transition-transform">
            <img 
              src="https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&q=80&w=150&h=150" 
              alt="Avatar"
              className="w-full h-full rounded-full border-2 border-white object-cover"
            />
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;
