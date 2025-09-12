import { ShoppingCart } from "lucide-react";

const CartIcon = ({ itemCount }) => {
  return (
    <div className="relative inline-block">
      <ShoppingCart className="w-6 h-6 text-white hover:text-gray-300 transition" />
      {itemCount > 0 && (
        <span className="absolute -top-2 -right-2 bg-red-500 text-white text-xs font-semibold rounded-full px-2 py-0.5">
          {itemCount}
        </span>
      )}
    </div>
  );
};

export default CartIcon;
