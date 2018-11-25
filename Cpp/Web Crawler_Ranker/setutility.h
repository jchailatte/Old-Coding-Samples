#include <iostream>
#include <set>

template <typename T>
std::set<T> intersectSet(std::set<T>& lhs, std::set<T>& rhs)
{
  std::set<T> result;
    
  typename std::set<T>::iterator lstart = lhs.begin();
  typename std::set<T>::iterator lend = lhs.end();
  
  typename std::set<T>::iterator rstart = rhs.begin();
  typename std::set<T>::iterator rend = rhs.end();

  while (lstart != lend  && rstart != rend)
  {
    if (*lstart<*rstart)
    {
        lstart++;
    }
    else if (*rstart<*lstart)
    {
        rstart++;
    }
    else
    {
      result.insert(*lstart);
      lstart++;
      rstart++;
    }
  }
    
  return result;
}

template <typename T>
std::set<T> unionSet(std::set<T>& lhs, std::set<T>& rhs)
{
    lhs.insert(rhs.begin(),rhs.end());

    return lhs;
}