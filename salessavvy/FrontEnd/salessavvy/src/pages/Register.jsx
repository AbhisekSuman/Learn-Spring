import { useState } from "react";

const RegisterForm = ({role}) => {
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
    role: role, // Default role
  });

  const [error, setError] = useState(null);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null); // Reset error before new request

    try {
      const response = await fetch("http://localhost:9090/api/user/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      const result = await response.json();

      if (response.ok) {
        // Redirect to login page if registration is successful
        alert("Registration successful! Please log in.");
        console.log(result);
      } else if (response.status === 400) {
        setError(result.error || "Registration failed");
      }
    } catch (err) {
      setError("Something went wrong. Try again later.");
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-black">
      <div className="w-full max-w-md bg-[#111] p-8 rounded-lg shadow-lg border border-gray-800">
        <h2 className="text-2xl font-bold text-white text-center mb-6">Create an Account</h2>

        {error && <p className="text-red-500 text-sm text-center mb-4">{error}</p>}

        <form onSubmit={handleSubmit} className="space-y-4">
          {/* Username */}
          <div>
            <label className="block text-gray-400 text-sm mb-1">Username</label>
            <input
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              className="w-full bg-[#222] text-white px-4 py-2 rounded-md border border-gray-700 focus:ring-2 focus:ring-gray-500 outline-none"
              required
            />
          </div>

          {/* Email */}
          <div>
            <label className="block text-gray-400 text-sm mb-1">Email</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className="w-full bg-[#222] text-white px-4 py-2 rounded-md border border-gray-700 focus:ring-2 focus:ring-gray-500 outline-none"
              required
            />
          </div>

          {/* Password */}
          <div>
            <label className="block text-gray-400 text-sm mb-1">Password</label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              className="w-full bg-[#222] text-white px-4 py-2 rounded-md border border-gray-700 focus:ring-2 focus:ring-gray-500 outline-none"
              required
            />
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            className="w-full bg-white text-black font-medium py-2 rounded-md hover:bg-gray-200 transition"
          >
            Register
          </button>
        </form>

        <p className="text-gray-500 text-center text-sm mt-4">
          Already have an account?{" "}
          <a href="/login" className="text-gray-300 hover:underline">
            Log in
          </a>
        </p>
      </div>
    </div>
  );
};

export default RegisterForm;
