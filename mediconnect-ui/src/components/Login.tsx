import React from 'react';
import { Activity, Mail, Lock, ArrowRight } from 'lucide-react';

interface LoginProps {
  onLogin: () => void;
}

const Login: React.FC<LoginProps> = ({ onLogin }) => {
  return (
    <div className="min-h-screen w-full flex items-center justify-center bg-gradient-to-br from-[#E0F2FE] to-[#F9FAFB] p-6">
      {/* Main Login Card */}
      <div className="w-full max-w-[450px] bg-white rounded-[40px] shadow-[0_20px_50px_rgba(0,0,0,0.05)] p-10 relative overflow-hidden">
        
        {/* Top Branding */}
        <div className="flex flex-col items-center mb-10">
          <div className="w-14 h-14 bg-gradient-to-tr from-blue-600 to-teal-400 rounded-2xl flex items-center justify-center text-white shadow-lg shadow-blue-200 mb-4">
            <Activity size={32} />
          </div>
          <h1 className="text-3xl font-extrabold text-slate-dark tracking-tight">MediConnect</h1>
          <p className="text-gray-400 font-medium mt-1">Welcome back, Specialist</p>
        </div>

        {/* Form */}
        <form className="space-y-6" onSubmit={(e) => { e.preventDefault(); onLogin(); }}>
          <div className="space-y-2">
            <label className="text-xs font-bold text-gray-400 uppercase tracking-widest ml-1">Email Address</label>
            <div className="relative group">
              <Mail className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-blue-500 transition-colors" size={20} />
              <input 
                type="email" 
                placeholder="doctor@mediconnect.com"
                className="w-full bg-gray-50 border-2 border-transparent focus:border-blue-500/10 focus:bg-white rounded-2xl py-4 pl-12 pr-4 text-sm transition-all outline-none"
              />
            </div>
          </div>

          <div className="space-y-2">
            <div className="flex justify-between items-center">
              <label className="text-xs font-bold text-gray-400 uppercase tracking-widest ml-1">Password</label>
              <a href="#" className="text-[11px] font-bold text-blue-500 hover:underline">Forgot?</a>
            </div>
            <div className="relative group">
              <Lock className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-blue-500 transition-colors" size={20} />
              <input 
                type="password" 
                placeholder="••••••••"
                className="w-full bg-gray-50 border-2 border-transparent focus:border-blue-500/10 focus:bg-white rounded-2xl py-4 pl-12 pr-4 text-sm transition-all outline-none"
              />
            </div>
          </div>

          <button 
            type="submit"
            className="w-full bg-gradient-to-r from-blue-600 to-blue-400 hover:from-blue-700 hover:to-blue-500 text-white font-bold py-4 rounded-full shadow-lg shadow-blue-200 flex items-center justify-center gap-2 group transition-all transform hover:-translate-y-0.5"
          >
            Sign In
            <ArrowRight size={18} className="group-hover:translate-x-1 transition-transform" />
          </button>
        </form>

        {/* Signup Link */}
        <p className="text-center mt-8 text-sm text-gray-400 font-medium">
          New to the platform? <a href="#" className="text-blue-600 font-bold hover:underline">Create Account</a>
        </p>

        {/* Signature - CRITICAL REQUIREMENT */}
        <div className="mt-12 text-center">
          <p className="text-[10px] text-gray-300 font-medium uppercase tracking-[0.2em]">
            ✨ Crafted by Vikash Kumar
          </p>
        </div>

        {/* Subtle decorative element */}
        <div className="absolute top-0 right-0 w-32 h-32 bg-blue-50 rounded-full -mr-16 -mt-16 opacity-50"></div>
      </div>
    </div>
  );
};

export default Login;
